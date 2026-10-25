1. Files read: bikeshare-good/src/main/java/bikeshare/Main.java, BikeShareService.java, Station.java, Bike.java, MemberType.java, Rental.java, Receipt.java, RideLog.java, CsvRideLog.java

2. Findings

| No. | Check | Code location (file:line) | Finding | Severity | Suggested fix |
| --- | --- | --- | --- | --- | --- |
| 1 | Check 1 | BikeShareService.java:39-43 | The bike is returned before the ride is written to the log. If append throws IOException, the ride is closed but no receipt is shown. | Should fix | Write the log first, or tell the user that only the saving failed. |
| 2 | Check 1 | Main.java:29-30 and BikeShareService.java:33 | The check for negative minutes is in the menu only, so the service accepts a negative fee. | Should fix | Check the minutes in giveBack. |
| 3 | Check 1 | Main.java:37-39 | Missing words are handled by catching ArrayIndexOutOfBoundsException, and IllegalArgumentException also hides number format errors. | Note | Check w.length and report number errors separately. |
| 4 | Check 1 | BikeShareService.java:16-19 | The station names and the bike numbers are written in the constructor. | Note | Pass them in as data. |
| 5 | Check 2 | Main.java:22-41 | run has logical cohesion, because the command word chooses what it does. | Note | Use one small method for each command. |
| 6 | Check 2 | BikeShareService.java:41-42 | rental.bike().getId() and rental.type().feeFor(minutes) reach through Rental, which touches the Law of Demeter. | Note | Add getBikeId() and fee(minutes) to Rental. |
| 7 | Check 2 | BikeShareService.java:13-22 | The constructor also builds the stations and bikes, which is a second job for the class. | Note | Create the stations outside and pass them in. |
| 8 | Check 2 | Main.java:25 | Connascence of convention. The words that the user types must match the enum names STUDENT and STAFF. | Note | Keep a mapping from the user's words to the enum. |
| 9 | Check 2 | BikeShareService.java:33, Main.java:32 | Connascence of position. memberId and stationName are two Strings in a row. | Note | Pass a small request object. |

Not found: fields that are not private, operations that return an internal collection, any interaction tighter than data coupling, and classes that depend on many other classes.

3. Number of findings
- Check 1 (code quality): 4
- Check 2 (cohesion, coupling and connascence): 5