1. Files read: parcelpoint/src/main/java/parcelpoint/Locker.java, Main.java, Parcel.java, ParcelService.java

2. Findings

| No. | Check | Code location (file:line) | Finding | Severity | Suggested fix |
| --- | --- | --- | --- | --- | --- |
| 1 | Check 1 | ParcelService.java:42 | Strings are compared with == in pickUp. The comparison never matches, so a pickup never frees the locker and the fee is always 0. | Must fix | Use parcel.getCode().equals(code). |
| 2 | Check 1 | ParcelService.java:76 | The FileWriter is never closed or flushed, so parcels.csv stays empty. | Must fix | Use try-with-resources. |
| 3 | Check 1 | ParcelService.java:80 | The catch block for IOException is empty, so save errors are hidden. | Should fix | Report the error, or add a comment that explains why it is ignored. |
| 4 | Check 1 | ParcelService.java:49 | pickUp returns 0 when the code is not found, which looks the same as a free pickup. | Should fix | Return a value or throw an exception that tells the two cases apart. |
| 5 | Check 1 | Main.java:11-14 | Input is not checked. A store command with too few words throws ArrayIndexOutOfBoundsException, and a non-number size or days throws NumberFormatException. | Should fix | Check words.length and catch NumberFormatException. |
| 6 | Check 1 | Main.java:8 | The Scanner on System.in is never closed. | Note | Wrap it in try-with-resources. |
| 7 | Check 1 | ParcelService.java:6 | The import java.util.Date is unused. | Should fix | Remove the import. |
| 8 | Check 1 | ParcelService.java:12 | The private field maxParcels is never used. | Should fix | Remove it, or enforce the limit in storeParcel. |
| 9 | Check 1 | Main.java:6-25 | main holds a chain of five branches in one loop, which is harder to read than needed. | Note | Move each command into its own method. |
| 10 | Check 1 | ParcelService.java:53-61 | The fee uses the unnamed numbers 3, 10.0, 15.0 and 20.0. | Note | Name them as constants. |
| 11 | Check 2 | ParcelService.java:24-37 | storeParcel has procedural cohesion. It finds a locker, sets the status, adds the parcel, sends the SMS and saves the file. | Should fix | Move the SMS and the saving to their own classes. |
| 12 | Check 2 | ParcelService.java:39-50 | pickUp has procedural cohesion. It finds the parcel, frees the locker, sets the status, saves the file and calculates the fee. | Should fix | Split the lookup, the saving and the fee into separate steps. |
| 13 | Check 2 | ParcelService.java:64-72 and Main.java:19,21 | printParcels has logical cohesion, and its boolean flag forStaff gives control coupling with Main. | Should fix | Use two methods, one for waiting parcels and one for the staff list. |
| 14 | Check 2 | Main.java:6-25 | main has logical cohesion, because the command word chooses what the code does. | Note | Use one handler method per command. |
| 15 | Check 2 | ParcelService.java:9 | The class has weak class cohesion. It handles lockers, SMS messages, fee rules, printing and file saving. fee (line 52) uses only its parameters and none of the class's attributes. | Should fix | Move the fee rules to a FeePolicy class and the saving to its own class. |
| 16 | Check 2 | ParcelService.java:28,44 and Parcel.java:7 | ParcelService sets parcel.status directly. This is content coupling to a public field. | Must fix | Make status private and add methods such as markStored() and markPickedUp(). |
| 17 | Check 2 | Parcel.java:7 | The field status is public. | Must fix | Make it private. |
| 18 | Check 2 | ParcelService.java:20-22 and Main.java:22 | getLockers returns the internal list, and Main reaches through the service to Locker with service.getLockers().stream().filter(Locker::isFree). This is content coupling and breaks the Law of Demeter. | Should fix | Add countFreeLockers() to ParcelService. |
| 19 | Check 2 | ParcelService.java:52 | fee takes a whole Parcel but uses only getSize(), which is stamp coupling. | Note | Pass the size only. |
| 20 | Check 2 | Locker.java:5, Parcel.java:6, ParcelService.java:15-17,26,56-58 | Connascence of convention. The size codes 1, 2 and 3 must mean the same in several classes. | Should fix | Use an enum Size. |
| 21 | Check 2 | Parcel.java:13, ParcelService.java:28,44,68 | Connascence of convention. The status strings "NEW", "STORED" and "PICKED_UP" must match in several places. | Should fix | Use an enum ParcelStatus. |
| 22 | Check 2 | Main.java:13 and Parcel.java:9 | Connascence of position. The constructor takes two Strings in a row, code and studentId, so swapped values still compile. | Note | Pass the values through named setters or a builder. |

Not found: methods that are too long, string literals repeated many times, classes that depend on many other classes, and connascence of algorithm (nothing reads parcels.csv back).

3. Number of findings
- Check 1 (code quality): 10
- Check 2 (cohesion, coupling and connascence): 12