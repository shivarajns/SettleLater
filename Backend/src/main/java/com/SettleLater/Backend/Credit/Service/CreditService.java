package com.SettleLater.Backend.Credit.Service;

import com.SettleLater.Backend.Credit.DTO.CreateCreditRequest;
import com.SettleLater.Backend.Credit.DTO.CreditResponse;
import com.SettleLater.Backend.Credit.Model.Credit;
import com.SettleLater.Backend.Credit.Model.CreditStatus;
import com.SettleLater.Backend.Credit.Repository.CreditRepository;
import com.SettleLater.Backend.CreditAccount.Exceptions.*;
import com.SettleLater.Backend.CreditAccount.Model.CreditAccountEntity;
import com.SettleLater.Backend.CreditAccount.Repository.CreditAccountRepo;
import com.SettleLater.Backend.LedgerTransaction.Model.LedgerTransaction;
import com.SettleLater.Backend.LedgerTransaction.Model.TransactionType;
import com.SettleLater.Backend.LedgerTransaction.Repository.LedgerTransactionRepository;
import com.SettleLater.Backend.auth.model.User;
import com.SettleLater.Backend.customer.model.Customer;
import com.SettleLater.Backend.customer.repository.CustomerRepository;
import com.SettleLater.Backend.shop.model.ShopModel;
import com.SettleLater.Backend.shop.repository.ShopRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditService {

    private final CreditRepository creditRepository;
    private final CreditAccountRepo creditAccountRepository;
    private final LedgerTransactionRepository ledgerTransactionRepository;
    private final CustomerRepository customerRepository;
    private final ShopRepository shopRepository;

    @Transactional
    public Credit createCredit(
            String shopId,
            String customerId,
            CreateCreditRequest request
    ) {


        if (request == null) {
            throw new IllegalArgumentException(
                    "Credit request cannot be null"
            );
        }

        if (request.amount() == null ||
                request.amount().compareTo(BigDecimal.ZERO) <= 0) {

            throw new IllegalArgumentException(
                    "Credit amount must be greater than zero"
            );
        }

        if (request.dueDate() == null) {

            throw new IllegalArgumentException(
                    "Due date is required"
            );
        }

        LocalDate creditDate = LocalDate.now();

        if (request.dueDate().isBefore(creditDate)) {

            throw new IllegalArgumentException(
                    "Due date cannot be before credit date"
            );
        }

        User authenticatedUser = getAuthenticatedUser();


        ShopModel shop = shopRepository
                .findByShopId(shopId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Shop not found"
                        )
                );

        if (shop.getUser() == null) {

            throw new IllegalStateException(
                    "Shop does not have an owner"
            );
        }

        String shopOwnerId = shop.getUser().getUserId();


        if (!shopOwnerId.equals(
                authenticatedUser.getUserId())) {

            throw new SecurityException(
                    "You are not authorized to access this shop"
            );
        }


        Customer customer = customerRepository
                .findByCustomerId(customerId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Customer not found"
                        )
                );


        if (customer.getShop() == null) {

            throw new IllegalStateException(
                    "Customer is not associated with any shop"
            );
        }

        if (!shopId.equals(
                customer.getShop().getShopId())) {

            throw new SecurityException(
                    "Customer does not belong to this shop"
            );
        }


        CreditAccountEntity creditAccount =
                creditAccountRepository
                        .findByCustomerAndShop(
                                customer,
                                shop
                        )
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Credit account not found for customer"
                                )
                        );



        BigDecimal currentTotalCredit =
                creditAccount.getTotalCredit() != null
                        ? creditAccount.getTotalCredit()
                        : BigDecimal.ZERO;

        BigDecimal currentOutstandingBalance =
                creditAccount.getOutstandingBalance() != null
                        ? creditAccount.getOutstandingBalance()
                        : BigDecimal.ZERO;



        Credit credit = Credit.builder()
                .creditAccount(creditAccount)
                .originalAmount(request.amount())
                .outstandingAmount(request.amount())
                .creditDate(creditDate)
                .dueDate(request.dueDate())
                .description(request.description())
                .status(CreditStatus.UNSETTLED)
                .build();

        Credit savedCredit =
                creditRepository.save(credit);




        LedgerTransaction transaction =
                LedgerTransaction.builder()
                        .creditAccount(creditAccount)
                        .credit(savedCredit)
                        .transactionType(
                                TransactionType.CREDIT
                        )
                        .amount(request.amount())
                        .transactionDate(
                                LocalDateTime.now()
                        )
                        .description(
                                request.description()
                        )
                        .build();

        ledgerTransactionRepository.save(transaction);


        creditAccount.setTotalCredit(
                currentTotalCredit.add(
                        request.amount()
                )
        );

        creditAccount.setOutstandingBalance(
                currentOutstandingBalance.add(
                        request.amount()
                )
        );

        creditAccountRepository.save(creditAccount);

        return savedCredit;
    }

    @Transactional
    public List<CreditResponse> getAllCredits(
            String shopId,
            String customerId
    ) {
        User authenticatedUser = getAuthenticatedUser();

        ShopModel shop = shopRepository.findByShopId(shopId)
                .orElseThrow(() ->
                        new ShopNotFound("Shop Not Found")
                );

        if (!shop.getUser().getUserId()
                .equals(authenticatedUser.getUserId())) {

            throw new NotAuthenticatedException(
                    "You are not authorized to access this shop"
            );
        }

        Customer customer = customerRepository
                .findByCustomerId(customerId)
                .orElseThrow(() ->
                        new CustomerNotFound(
                                "Customer not found"
                        )
                );

        if (!customer.getShop()
                .getShopId()
                .equals(shopId)) {

            throw new CustomerDoesNotBelongsToShop(
                    "Customer does not belong to this shop"
            );
        }

        CreditAccountEntity creditAccount =
                creditAccountRepository
                        .findByShopAndCustomer(shop, customer)
                        .orElseThrow(() ->
                                new CreditAccountNotFound(
                                        "Credit account not found"
                                )
                        );

        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").descending());

        Page<Credit> credits =
                creditRepository.findByCreditAccount(
                        creditAccount,
                        pageable
                );

        return credits.stream()
                .map(this::mapToCreditResponse)
                .toList();
    }

    private CreditResponse mapToCreditResponse(Credit credit) {

        BigDecimal paidAmount =
                credit.getOriginalAmount()
                        .subtract(
                                credit.getOutstandingAmount()
                        );

        return CreditResponse.builder()
                .creditId(credit.getCreditId())
                .customerId(
                        credit.getCreditAccount()
                                .getCustomer()
                                .getCustomerId()
                )
                .originalAmount(
                        credit.getOriginalAmount()
                )
                .outstandingAmount(
                        credit.getOutstandingAmount()
                )
                .dueDate(
                        credit.getDueDate()
                )
                .description(
                        credit.getDescription()
                )
                .status(
                        credit.getStatus()
                )
                .creditDate(
                        LocalDate.from(credit.getCreatedAt())
                )
                .build();
    }


    @Transactional
    public CreditResponse getCreditById(
            String shopId,
            String customerId,
            String creditId
    ) {

        User authenticatedUser = getAuthenticatedUser();

        // 1. Find shop
        ShopModel shop = shopRepository
                .findByShopId(shopId)
                .orElseThrow(() ->
                        new ShopNotFound("Shop Not Found")
                );

        // 2. Verify authenticated user owns the shop
        if (shop.getUser() == null ||
                !shop.getUser()
                        .getUserId()
                        .equals(authenticatedUser.getUserId())) {

            throw new NotAuthenticatedException(
                    "You are not authorized to access this shop"
            );
        }

        // 3. Find customer
        Customer customer = customerRepository
                .findByCustomerId(customerId)
                .orElseThrow(() ->
                        new CustomerNotFound(
                                "Customer not found"
                        )
                );

        // 4. Verify customer belongs to this shop
        if (customer.getShop() == null ||
                !customer.getShop()
                        .getShopId()
                        .equals(shopId)) {

            throw new CustomerDoesNotBelongsToShop(
                    "Customer does not belong to this shop"
            );
        }

        // 5. Find customer's credit account for this shop
        CreditAccountEntity creditAccount =
                creditAccountRepository
                        .findByShopAndCustomer(
                                shop,
                                customer
                        )
                        .orElseThrow(() ->
                                new CreditAccountNotFound(
                                        "Credit account not found"
                                )
                        );

        // 6. Find credit
        Credit credit = creditRepository
                .findByCreditId(creditId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Credit not found"
                        )
                );

        // 7. Verify credit belongs to this credit account
        if (credit.getCreditAccount() == null ||
                !credit.getCreditAccount()
                        .getAccountId()
                        .equals(creditAccount.getAccountId())) {

            throw new SecurityException(
                    "Credit does not belong to this customer"
            );
        }

        // 8. Convert entity to response
        return mapToCreditResponse(credit);
    }



    private User getAuthenticatedUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated()) {

            throw new SecurityException(
                    "User is not authenticated"
            );
        }

        Object principal =
                authentication.getPrincipal();

        if (!(principal instanceof User user)) {

            throw new SecurityException(
                    "Unable to identify authenticated user"
            );
        }

        return user;
    }
}