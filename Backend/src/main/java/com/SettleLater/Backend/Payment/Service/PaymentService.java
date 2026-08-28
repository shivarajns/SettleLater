package com.SettleLater.Backend.Payment.Service;

import com.SettleLater.Backend.Credit.Model.Credit;
import com.SettleLater.Backend.Credit.Model.CreditStatus;
import com.SettleLater.Backend.Credit.Repository.CreditRepository;
import com.SettleLater.Backend.CreditAccount.Model.CreditAccountEntity;
import com.SettleLater.Backend.LedgerTransaction.Model.LedgerTransaction;
import com.SettleLater.Backend.LedgerTransaction.Model.TransactionType;
import com.SettleLater.Backend.LedgerTransaction.Repository.LedgerTransactionRepository;
import com.SettleLater.Backend.Payment.DTO.CreatePaymentRequest;
import com.SettleLater.Backend.Payment.Model.Payment;
import com.SettleLater.Backend.Payment.Model.PaymentStatus;
import com.SettleLater.Backend.Payment.Repository.PaymentRepository;
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
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final CreditRepository creditRepository;
    private final LedgerTransactionRepository ledgerTransactionRepository;
    private final CustomerRepository customerRepository;
    private final ShopRepository shopRepository;

    @Transactional
    public Payment createPayment(
            String shopId,
            String customerId,
            String creditId,
            CreatePaymentRequest request
    ) {

        System.out.println("===== CREATE PAYMENT START =====");
        System.out.println("shopId = " + shopId);
        System.out.println("customerId = " + customerId);
        System.out.println("creditId = " + creditId);
        System.out.println("amount = " + request.amount());


        User authenticatedUser = getAuthenticatedUser();



        ShopModel shop = shopRepository.findByShopId(shopId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Shop not found")
                );



        if (!shop.getUser().getUserId()
                .equals(authenticatedUser.getUserId())) {

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



        if (!customer.getShop().getShopId().equals(shopId)) {

            throw new SecurityException(
                    "Customer does not belong to this shop"
            );
        }



        Credit credit = creditRepository
                .findByCreditId(creditId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Credit not found"
                        )
                );



        if (!credit.getCreditAccount()
                .getCustomer()
                .getCustomerId()
                .equals(customerId)) {

            throw new SecurityException(
                    "Credit does not belong to this customer"
            );
        }



        CreditAccountEntity creditAccount =
                credit.getCreditAccount();

        if (!creditAccount.getShop()
                .getShopId()
                .equals(shopId)) {

            throw new SecurityException(
                    "Credit does not belong to this shop"
            );
        }



        if (credit.getStatus() == CreditStatus.SETTLED) {

            throw new IllegalStateException(
                    "Cannot make payment for a settled credit"
            );
        }



        BigDecimal paymentAmount = request.amount();

        if (paymentAmount == null ||
                paymentAmount.compareTo(BigDecimal.ZERO) <= 0) {

            throw new IllegalArgumentException(
                    "Payment amount must be greater than zero"
            );
        }



        if (paymentAmount.compareTo(
                credit.getOutstandingAmount()
        ) > 0) {

            throw new IllegalArgumentException(
                    "Payment amount exceeds outstanding amount"
            );
        }



        Payment payment = Payment.builder()
                .credit(credit)
                .creditAccount(creditAccount)
                .amount(paymentAmount)
                .status(PaymentStatus.RECORDED)
                .paymentDate(LocalDateTime.now())
                .description(request.description())
                .build();

        Payment savedPayment =
                paymentRepository.save(payment);


        BigDecimal newCreditOutstanding =
                credit.getOutstandingAmount()
                        .subtract(paymentAmount);

        credit.setOutstandingAmount(
                newCreditOutstanding
        );


        if (newCreditOutstanding.compareTo(
                BigDecimal.ZERO
        ) == 0) {

            credit.setStatus(CreditStatus.SETTLED);

        } else {

            credit.setStatus(CreditStatus.UNSETTLED);
        }

        creditRepository.save(credit);


        BigDecimal newAccountOutstanding =
                creditAccount.getOutstandingBalance()
                        .subtract(paymentAmount);

        creditAccount.setOutstandingBalance(
                newAccountOutstanding
        );

        creditAccount.setTotalPaid(
                creditAccount.getTotalPaid()
                        .add(paymentAmount)
        );


        LedgerTransaction transaction =
                LedgerTransaction.builder()
                        .creditAccount(creditAccount)
                        .credit(credit)
                        .transactionType(TransactionType.PAYMENT)
                        .amount(paymentAmount)
                        .transactionDate(LocalDateTime.now())
                        .description(request.description())
                        .build();

        ledgerTransactionRepository.save(transaction);



        System.out.println("===== CREATE PAYMENT SUCCESS =====");
        System.out.println(
                "Payment ID = " + savedPayment.getPaymentId()
        );
        System.out.println(
                "Payment Amount = " + paymentAmount
        );
        System.out.println(
                "Remaining Credit Outstanding = "
                        + newCreditOutstanding
        );

        return savedPayment;
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