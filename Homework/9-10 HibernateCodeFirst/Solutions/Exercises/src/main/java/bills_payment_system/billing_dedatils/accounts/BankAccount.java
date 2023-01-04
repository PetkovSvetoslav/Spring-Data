package bills_payment_system.billing_dedatils.accounts;

import bills_payment_system.billing_dedatils.BillingDetail;

import javax.persistence.*;

@Entity
@Table(name = "bank_accounts")
public class BankAccount extends BillingDetail {
    private String name;
    private String SWIFTCode;

    public BankAccount() {
    }

    public BankAccount(String name, String SWIFTCode) {
        this.name = name;
        this.SWIFTCode = SWIFTCode;
    }

    @Column(nullable = false)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "SWIFT_code", nullable = false, length = 11)
    public String getSWIFTCode() {
        return this.SWIFTCode;
    }

    public void setSWIFTCode(String SWIFTCode) {
        this.SWIFTCode = SWIFTCode;
    }
}