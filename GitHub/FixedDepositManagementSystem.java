// 1. Custom Exceptions
class AccountNotFoundException extends Exception {
public AccountNotFoundException(String msg) {
super(msg);
}
}
class InvalidAmountException extends Exception {
public InvalidAmountException(String msg) {
super(msg);
}
}
class PrematureWithdrawalException extends Exception {
public PrematureWithdrawalException(String msg) {
super(msg);
}
}
// 2. Interface
interface FDOperations {
void openFD(int accNo, String name, double amount, int term) throws InvalidAmountException;
void viewFD(int accNo) throws AccountNotFoundException;
void withdrawFD(int accNo, int yearsPassed) throws AccountNotFoundException, PrematureWithdrawalException;
void viewAllFDs();
}
// 3. FD Account Class
class FDAccount {
int accNo;
String holderName;
double principal;
int term; // in years
double interestRate; // fixed for simplicity
boolean isActive;
FDAccount(int accNo, String holderName, double principal, int term, double interestRate) {
this.accNo = accNo;
this.holderName = holderName;
this.principal = principal;
this.term = term;
this.interestRate = interestRate;
this.isActive = true;
}
double maturityAmount() {
return principal * Math.pow((1 + interestRate / 100), term);
}
public String toString() {
return "FD Account No: " + accNo + ", Name: " + holderName + ", Principal: ₹" + principal +
", Term: " + term + " years, Rate: " + interestRate + "%, Active: " + isActive;
}
}
// 4. FD Management Implementation
class FDManagement implements FDOperations {
FDAccount[] accounts;
int count;
FDManagement(int size) {
accounts = new FDAccount[size];
count = 0;
}
private FDAccount findAccount(int accNo) throws AccountNotFoundException {
for (int i = 0; i < count; i++) {
if (accounts[i].accNo == accNo && accounts[i].isActive) return accounts[i];
}
throw new AccountNotFoundException("FD Account " + accNo + " not found!");
}
@Override
public void openFD(int accNo, String name, double amount, int term) throws InvalidAmountException {
if (amount < 5000) throw new InvalidAmountException("Minimum FD amount is ₹5000!");
accounts[count++] = new FDAccount(accNo, name, amount, term, 6.5);
System.out.println(" FD opened successfully for " + name + " with Account No: " + accNo);
}
@Override
public void viewFD(int accNo) throws AccountNotFoundException {
FDAccount acc = findAccount(accNo);
System.out.println(acc);
System.out.println(" Maturity Amount: ₹" + acc.maturityAmount());
}
@Override
public void withdrawFD(int accNo, int yearsPassed) throws AccountNotFoundException, PrematureWithdrawalException {
FDAccount acc = findAccount(accNo);
if (yearsPassed < acc.term) {
throw new PrematureWithdrawalException("FD cannot be withdrawn before " + acc.term + " years!");
}
System.out.println(" FD closed for " + acc.holderName + ". Paid ₹" + acc.maturityAmount());
acc.isActive = false;
}
@Override
public void viewAllFDs() {
System.out.println("\n--- All FD Accounts ---");
for (int i = 0; i < count; i++) {
if (accounts[i].isActive) System.out.println(accounts[i]);
}
}
}
// 5. Main Application
class FixedDepositApp {
public static void main(String[] args) {
java.util.Scanner sc = new java.util.Scanner(System.in);
FDManagement bank = new FDManagement(10);
int choice;
do {
System.out.println("\n===== Fixed Deposit Management Menu =====");
System.out.println("1. Open FD");
System.out.println("2. View FD");
System.out.println("3. Withdraw FD");
System.out.println("4. View All FDs");
System.out.println("5. Exit");
System.out.print("Enter choice: ");
choice = sc.nextInt();
try {
switch (choice) {
case 1:
System.out.print("Enter Account No: ");
int accNo = sc.nextInt();
sc.nextLine();
System.out.print("Enter Holder Name: ");
String name = sc.nextLine();
System.out.print("Enter Principal Amount: ");
double amt = sc.nextDouble();
System.out.print("Enter Term (Years): ");
int term = sc.nextInt();
bank.openFD(accNo, name, amt, term);
break;
case 2:
System.out.print("Enter Account No: ");
int vAcc = sc.nextInt();
bank.viewFD(vAcc);
break;
case 3:
System.out.print("Enter Account No: ");
int wAcc = sc.nextInt();
System.out.print("Enter Years Passed: ");
int years = sc.nextInt();
bank.withdrawFD(wAcc, years);
break;
case 4:
bank.viewAllFDs();
break;
case 5:
System.out.println(" Exiting FD Management...");
break;
default:
System.out.println(" Invalid choice!");
}
} catch (Exception e) {
System.out.println(" Error: " + e.getMessage());
}
} while (choice != 5);
sc.close();
}
}