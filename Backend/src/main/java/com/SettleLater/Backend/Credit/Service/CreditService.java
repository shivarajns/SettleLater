package com.SettleLater.Backend.Credit.Service;

import com.SettleLater.Backend.Credit.DTO.CreateCreditRequest;
import com.SettleLater.Backend.Credit.Model.Credit;
import com.SettleLater.Backend.Credit.Model.CreditStatus;
import com.SettleLater.Backend.Credit.Repository.CreditRepository;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

        System.out.println("===== CREATE CREDIT START =====");

        // ---------------------------------------------------------
        // 1. Validate request
        // ---------------------------------------------------------

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

        System.out.println("shopId = " + shopId);
        System.out.println("customerId = " + customerId);
        System.out.println("amount = " + request.amount());
        System.out.println("dueDate = " + request.dueDate());


        // ---------------------------------------------------------
        // 2. Get authenticated user
        // ---------------------------------------------------------

        User authenticatedUser = getAuthenticatedUser();

        System.out.println(
                "Authenticated User ID = "
                        + authenticatedUser.getUserId()
        );


        // ---------------------------------------------------------
        // 3. Find shop
        // ---------------------------------------------------------

        ShopModel shop = shopRepository
                .findByShopId(shopId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Shop not found"
                        )
                );

        System.out.println("===== SHOP FOUND =====");
        System.out.println("Shop ID = " + shop.getShopId());


        // ---------------------------------------------------------
        // 4. Verify shop ownership
        // ---------------------------------------------------------

        if (shop.getUser() == null) {

            throw new IllegalStateException(
                    "Shop does not have an owner"
            );
        }

        String shopOwnerId = shop.getUser().getUserId();

        System.out.println(
                "Shop Owner ID = " + shopOwnerId
        );

        if (!shopOwnerId.equals(
                authenticatedUser.getUserId())) {

            throw new SecurityException(
                    "You are not authorized to access this shop"
            );
        }


        // ---------------------------------------------------------
        // 5. Find customer
        // ---------------------------------------------------------

        Customer customer = customerRepository
                .findByCustomerId(customerId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Customer not found"
                        )
                );

        System.out.println("===== CUSTOMER FOUND =====");
        System.out.println(
                "Customer ID = " + customer.getCustomerId()
        );


        // ---------------------------------------------------------
        // 6. Verify customer belongs to this shop
        // ---------------------------------------------------------

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


        // ---------------------------------------------------------
        // 7. Find Credit Account
        // ---------------------------------------------------------

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

        System.out.println(
                "===== CREDIT ACCOUNT FOUND ====="
        );

        System.out.println(
                "Account ID = "
                        + creditAccount.getAccountId()
        );

        System.out.println(
                "Total Credit = "
                        + creditAccount.getTotalCredit()
        );

        System.out.println(
                "Total Paid = "
                        + creditAccount.getTotalPaid()
        );

        System.out.println(
                "Outstanding Balance = "
                        + creditAccount.getOutstandingBalance()
        );


        // ---------------------------------------------------------
        // 8. Protect against NULL financial values
        // ---------------------------------------------------------

        BigDecimal currentTotalCredit =
                creditAccount.getTotalCredit() != null
                        ? creditAccount.getTotalCredit()
                        : BigDecimal.ZERO;

        BigDecimal currentOutstandingBalance =
                creditAccount.getOutstandingBalance() != null
                        ? creditAccount.getOutstandingBalance()
                        : BigDecimal.ZERO;


        // ---------------------------------------------------------
        // 9. Create Credit
        // ---------------------------------------------------------

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

        System.out.println(
                "===== CREDIT CREATED ====="
        );

        System.out.println(
                "Credit ID = " + savedCredit.getId()
        );


        // ---------------------------------------------------------
        // 10. Create Ledger Transaction
        // ---------------------------------------------------------

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

        System.out.println(
                "===== LEDGER TRANSACTION CREATED ====="
        );


        // ---------------------------------------------------------
        // 11. Update Credit Account
        // ---------------------------------------------------------

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

        System.out.println(
                "===== CREDIT ACCOUNT UPDATED ====="
        );

        System.out.println(
                "New Total Credit = "
                        + creditAccount.getTotalCredit()
        );

        System.out.println(
                "New Outstanding Balance = "
                        + creditAccount.getOutstandingBalance()
        );


        // ---------------------------------------------------------
        // 12. Complete
        // ---------------------------------------------------------

        System.out.println(
                "===== CREATE CREDIT SUCCESS ====="
        );

        return savedCredit;
    }


    // =============================================================
    // AUTHENTICATED USER
    // =============================================================

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