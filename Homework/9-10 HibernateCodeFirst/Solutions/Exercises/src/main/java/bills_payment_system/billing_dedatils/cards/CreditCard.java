package bills_payment_system.billing_dedatils.cards;

import bills_payment_system.billing_dedatils.BillingDetail;

import javax.persistence.*;

@Entity
@Table(name = "credit_cards")
public class CreditCard extends BillingDetail {
    private CardType cardType;
    private byte expirationMonth;
    private byte expirationYear;

    public CreditCard() {
    }

    public CreditCard(CardType cardType, byte expirationMonth, byte expirationYear) {
        this.cardType = cardType;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
    }

    @Enumerated(value = EnumType.ORDINAL)
    @Column(nullable = false)
    public CardType getCardType() {
        return this.cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    @Column(name = "expiration_month", nullable = false)
    public byte getExpirationMonth() {
        return this.expirationMonth;
    }

    public void setExpirationMonth(byte expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    @Column(name = "expiration_year", nullable = false)
    public byte getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(byte expirationYear) {
        this.expirationYear = expirationYear;
    }
}