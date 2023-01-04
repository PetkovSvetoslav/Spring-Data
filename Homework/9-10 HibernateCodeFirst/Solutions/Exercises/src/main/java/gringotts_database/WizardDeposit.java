package gringotts_database;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "wizard_deposits")
public class WizardDeposit {
    private long id;
    private String fistName;
    private String lastName;
    private String notes;
    private byte age;
    private String magicWandCreator;
    private short magicWandSize;
    private String depositGroup;
    private LocalDateTime depositStartDate;
    private float depositAmount;
    private float depositInterest;
    private float depositCharge;
    private LocalDateTime depositExpirationDate;
    private boolean isDepositExpired;

    public WizardDeposit() {
    }

    public WizardDeposit(String fistName, String lastName, String notes, byte age,
                         String magicWandCreator, short magicWandSize, String depositGroup,
                         float depositAmount, float depositInterest, float depositCharge,
                         LocalDateTime depositExpirationDate, boolean isDepositExpired) {
        this.fistName = fistName;
        this.lastName = lastName;
        this.notes = notes;
        this.age = age;
        this.magicWandCreator = magicWandCreator;
        this.magicWandSize = magicWandSize;
        this.depositGroup = depositGroup;
        this.depositStartDate = LocalDateTime.now();
        this.depositAmount = depositAmount;
        this.depositInterest = depositInterest;
        this.depositCharge = depositCharge;
        this.depositExpirationDate = depositExpirationDate;
        this.isDepositExpired = isDepositExpired;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "first_name", length = 50)
    public String getFistName() {
        return this.fistName;
    }

    public void setFistName(String fistName) {
        this.fistName = fistName;
    }

    @Column(name = "last_name", length = 60, nullable = false)
    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(columnDefinition = "TEXT")
    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Column(columnDefinition = "TINYINT UNSIGNED", nullable = false)
    public byte getAge() {
        return this.age;
    }

    public void setAge(byte age) {
        this.age = age;
    }

    @Column(name = "magic_wand_creator", length = 100)
    public String getMagicWandCreator() {
        return this.magicWandCreator;
    }

    public void setMagicWandCreator(String magicWandCreator) {
        this.magicWandCreator = magicWandCreator;
    }

    @Column(name = "magic_wand_size", columnDefinition = "SMALLINT UNSIGNED")
    public short getMagicWandSize() {
        return this.magicWandSize;
    }

    public void setMagicWandSize(short magicWandSize) {
        this.magicWandSize = magicWandSize;
    }

    @Column(name = "deposit_group", length = 20)
    public String getDepositGroup() {
        return this.depositGroup;
    }

    public void setDepositGroup(String depositGroup) {
        this.depositGroup = depositGroup;
    }

    @Column(name = "deposit_start_date")
    public LocalDateTime getDepositStartDate() {
        return this.depositStartDate;
    }

    public void setDepositStartDate(LocalDateTime depositStartDate) {
        this.depositStartDate = depositStartDate;
    }

    @Column(name = "deposit_amount")
    public float getDepositAmount() {
        return this.depositAmount;
    }

    public void setDepositAmount(float depositAmount) {
        this.depositAmount = depositAmount;
    }

    @Column(name = "deposit_interest")
    public float getDepositInterest() {
        return this.depositInterest;
    }

    public void setDepositInterest(float depositInterest) {
        this.depositInterest = depositInterest;
    }

    @Column(name = "deposit_charge")
    public float getDepositCharge() {
        return this.depositCharge;
    }

    public void setDepositCharge(float depositCharge) {
        this.depositCharge = depositCharge;
    }

    @Column(name = "deposit_expiration_date")
    public LocalDateTime getDepositExpirationDate() {
        return this.depositExpirationDate;
    }

    public void setDepositExpirationDate(LocalDateTime depositExpirationDate) {
        this.depositExpirationDate = depositExpirationDate;
    }

    @Column(name = "is_deposit_expired")
    public boolean isDepositExpired() {
        return this.isDepositExpired;
    }

    public void setDepositExpired(boolean depositExpired) {
        this.isDepositExpired = depositExpired;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WizardDeposit that = (WizardDeposit) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "WizardDeposit:" + System.lineSeparator() +
                "   id = " + id + System.lineSeparator() +
                "   fistName = " + fistName + System.lineSeparator() +
                "   lastName = " + lastName + System.lineSeparator() +
                "   notes = " + notes + System.lineSeparator() +
                "   age = " + age + System.lineSeparator() +
                "   magicWandCreator = " + magicWandCreator + System.lineSeparator() +
                "   magicWandSize = " + magicWandSize + System.lineSeparator() +
                "   depositGroup = " + depositGroup + System.lineSeparator() +
                "   depositStartDate = " + depositStartDate + System.lineSeparator() +
                "   depositAmount = " + depositAmount + System.lineSeparator() +
                "   depositInterest = " + depositInterest + System.lineSeparator() +
                "   depositCharge = " + depositCharge + System.lineSeparator() +
                "   depositExpirationDate = " + depositExpirationDate + System.lineSeparator() +
                "   isDepositExpired = " + isDepositExpired;
    }
}