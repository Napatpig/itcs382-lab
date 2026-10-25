# Lab Report, Code Quality and Design Quality

Repository URL:

## Tools

| Item | Version or name |
| --- | --- |
| JDK |openjdk version "25.0.4.1" 2026-08-18 LTS |
| Maven |Apache Maven 3.10.0 |
| IDE |VS Code 1.143.1|
| SonarQube for IDE |6.2.0|
| AI tool and exact model |Claude Sonnet 5.5 |

## Task 1. SonarQube on the starter code

Commit of the starter, before any change: 0a8ddde

Table 1. SonarQube issues in the starter code

| No. | Rule | File:line | Message |
| --- | --- | --- | --- |
| 1 | java:S106 | Main.java:9 | Replace this use of System.out by a logger. |
| 2 | java:S106 | Main.java:15 | Replace this use of System.out by a logger. |
| 3 | java:S106 | Main.java:17 | Replace this use of System.out by a logger. |
| 4 | java:S106 | Main.java:22 | Replace this use of System.out by a logger. |
| 5 | java:S2143 | ParcelService.java:1 | Use the "java.time" API for date and time. |
| 6 | java:S1128 | ParcelService.java:6 | Remove this unused import 'java.util.Date'. |
| 7 | java:S1068 | ParcelService.java:12 | Remove this unused "maxParcels" private field. |
| 8 | java:S106 | ParcelService.java:30 | Replace this use of System.out by a logger. |
| 9 | java:S4973 | ParcelService.java:42 | Strings and Boxed types should be compared using "equals()". |
| 10 | java:S106 | ParcelService.java:67 | Replace this use of System.out by a logger. |
| 11 | java:S106 | ParcelService.java:69 | Replace this use of System.out by a logger. |
| 12 | java:S2095 | ParcelService.java:76 | Use try-with-resources or close this "FileWriter" in a "finally" clause. |
| 13 | java:S108 | ParcelService.java:80 | Remove this block of code, fill it in, or add a comment explaining why it is empty. |


## Task 2. AI review of the starter code

| Item | Value |
| --- | --- |
| Date | 25 October 2026 |
| AI tool and exact model | Claude, Claude Sonnet 5.5 |
| Commit reviewed | 0a8ddde |
| How the AI read the files (attached the files, opened the folder, or pasted the files) | Attached the four Java files in the chat |
| Output file in the reviews folder | reviews/parcelpoint-review.md |

Table 2. All findings on the starter code

| No. | File:line | Finding | Found by (SonarQube, AI or both) | SonarQube rule or course term | Correct? (yes or no, and why) |
| --- | --- | --- | --- | --- | --- |
| 1 | ParcelService.java:42 | Strings compared with == in pickUp, so pickup never matches and the fee is always 0 | both | java:S4973 | yes, the program printed Fee: 0.0 for pickup P1 5 and staff still showed STORED |
| 2 | ParcelService.java:76 | FileWriter never closed, so parcels.csv stays empty | both | java:S2095 | yes, parcels.csv was empty after the run in C2 |
| 3 | ParcelService.java:80 | Empty catch block hides save errors | both | java:S108 | yes, line 80 is catch (IOException e) with an empty body |
| 4 | ParcelService.java:6 | Unused import java.util.Date | both | java:S1128 | yes, Date is not used anywhere in the file |
| 5 | ParcelService.java:12 | Unused private field maxParcels | both | java:S1068 | yes, maxParcels is never read |
| 6 | ParcelService.java:1 | Use the java.time API for date and time | SonarQube | java:S2143 | yes, but it is raised only because of the unused Date import, so it is a side effect of row 4 |
| 7 | Main.java:9,15,17,22 | System.out used instead of a logger | SonarQube | java:S106 | yes by the rule, but acceptable here because a command-line program prints to the console |
| 8 | ParcelService.java:30,67,69 | System.out used instead of a logger | SonarQube | java:S106 | yes by the rule, but lines 67 and 69 are the printed output of list and staff |
| 9 | ParcelService.java:49 | pickUp returns 0 when the code is not found, the same as a free pickup | AI | code smell, unclear result | yes, return 0 at line 49 cannot be told apart from fee 0 at line 54 |
| 10 | Main.java:11-14 | No input check, so a short store command or a non-number size throws an exception | AI | probable bug | yes, words[1], words[2] and Integer.parseInt(words[3]) are unchecked |
| 11 | Main.java:8 | Scanner on System.in never closed | AI | resource not closed | yes, there is no close() or try-with-resources (the Java extension also warns, but it is not a SonarQube issue) |
| 12 | Main.java:6-25 | main has a chain of five command branches in one loop | AI | logical cohesion | yes, the command word chooses what the code does |
| 13 | ParcelService.java:53-61 | Unnamed numbers 3, 10.0, 15.0, 20.0 in fee | AI | code smell, magic numbers | yes, the numbers are written directly in the code |
| 14 | ParcelService.java:24-37 | storeParcel does lookup, status change, SMS and saving | AI | procedural cohesion | yes it does several jobs, but the steps share the same parcel, so the label could also be argued as communicational |
| 15 | ParcelService.java:39-50 | pickUp does lookup, locker release, status change, saving and fee | AI | procedural cohesion | yes it does several jobs, with the same doubt about the exact label |
| 16 | ParcelService.java:64-72, Main.java:19,21 | printParcels(boolean forStaff) changes behaviour by a flag | AI | control coupling, logical cohesion | yes, the flag at line 66 decides what is printed |
| 17 | ParcelService.java:9 | ParcelService handles lockers, SMS, fees, printing and saving | AI | weak class cohesion | yes, and fee (line 52) uses none of the class's attributes |
| 18 | Parcel.java:7 | Field status is public | AI | not private field, encapsulation | yes, line 7 is public String status |
| 19 | ParcelService.java:28,44 | ParcelService writes parcel.status directly | AI | content coupling | yes, this is the same kind of access as a.height in the lecture |
| 20 | ParcelService.java:20-22, Main.java:22 | getLockers returns the internal list and Main reaches through it | AI | content coupling, Law of Demeter | yes, Main calls service.getLockers().stream().filter(...) |
| 21 | ParcelService.java:52 | fee takes a whole Parcel but uses only getSize() | AI | stamp coupling | yes, only parcel.getSize() is used |
| 22 | Locker.java:5, Parcel.java:6, ParcelService.java:15-17,26,56-58 | Size codes 1, 2, 3 must mean the same in several classes | AI | connascence of convention | yes, the comments 1 = small, 2 = medium, 3 = large are the only definition |
| 23 | Parcel.java:13, ParcelService.java:28,44,68 | Status strings "NEW", "STORED", "PICKED_UP" must match in several places | AI | connascence of convention | yes, line 68 compares with the string "STORED" |
| 24 | Main.java:13, Parcel.java:9 | Constructor takes two Strings in a row, code and studentId | AI | connascence of position | no, too weak, because swapped values would be an input mistake, not a likely design fault |

2.1 Name two problems that only the AI review found.
The public field status in Parcel.java:7 and the content coupling that it causes, because ParcelService sets parcel.status directly (rows 18 and 19). SonarQube reported nothing for Parcel.java. The AI also found that the size codes 1, 2 and 3 and the status strings are connascence of convention (rows 22 and 23), which SonarQube has no rule for here.

2.2 Name one problem that only SonarQube found, or write none.
java:S106 at Main.java:9,15,17,22 and ParcelService.java:30,67,69, which says to replace System.out with a logger. The AI review did not mention it.

2.3 Name one AI finding that was wrong or too vague, and explain why. If every finding was right, explain how you checked them.
Row 24 was too weak. It called two String parameters in a row connascence of position, but this is only a small risk of mixing up input, not a design problem that needs a change. Rows 14 and 15 are also debatable, because the steps of storeParcel and pickUp all work on the same parcel, so “communicational” could also be argued instead of “procedural”. I checked each finding by opening the file at the given line, and I compared findings 1 and 2 with the real output of the program from step C2.

## Task 3. Fix and check

Table 3. What you did with each SonarQube issue in Table 1

| No. in Table 1 | Fixed or kept | Commit, or the reason for keeping it |
| --- | --- | --- |
| 1 | Kept | System.out is the console output of a command-line program |
| 2 | Kept | System.out is the console output of a command-line program |
| 3 | Kept | System.out is the console output of a command-line program |
| 4 | Kept | System.out is the console output of a command-line program |
| 5 | Fixed | 3929cd8 (disappeared after removing the unused Date import) |
| 6 | Fixed | 3929cd8 |
| 7 | Fixed | 3929cd8 |
| 8 | Kept | System.out sends the SMS text, which this program simulates on the console |
| 9 | Fixed | 3929cd8 |
| 10 | Kept | System.out is the printed output of the list command |
| 11 | Kept | System.out is the printed output of the staff command |
| 12 | Fixed | 3929cd8 |
| 13 | Fixed | 3929cd8 |

Number of SonarQube issues that remain after your fixes: 8

The two design problems that you fixed

| Design problem (course term) | What you changed | Commit |
| --- | --- | --- |
| Content coupling and a public field (Table 2, rows 18 and 19) | Made Parcel.status private, and added getStatus(), markStored() and markPickedUp(), so ParcelService no longer writes the field directly | 3929cd8 |
| Reaching through an object, Law of Demeter (Table 2, row 20) | Added countFreeLockers() to ParcelService, so Main no longer calls getLockers().stream() | 3929cd8 |

Output of the program after your fixes, for the commands `store P1 6501 2`, `pickup P1 5` and `staff`, and the content of parcels.csv:
P1 6501 PICKED_UP
Free lockers: 3
P1,PICKED_UP

```text

```

## Task 4. AI-generated poor and good designs

| Item | bikeshare-poor | bikeshare-good |
| --- | --- | --- |
| Date |25 October 2026|25 October 2026|
| AI tool and exact model |Claude Sonnet 5.5|Claude Sonnet 5.5|
| Prompt (B or C as given, or your changed prompt in full) | B | C |
| Builds with mvn package (yes or no) |Yes|Yes|
| Number of classes, interfaces, enums and records |1|9|
| SonarQube issues, total |18|2|
| SonarQube issues, by rule |S106 ×11, S2386 ×5, S3776 ×1, S3358 ×1|S106 ×2|
| AI review findings in Check 1 |5|4|
| AI review findings in Check 2 |8|5|
| Review output file in the reviews folder |reviews/bikeshare-poor-review.md |reviews/bikeshare-good-review.md |

4.1 Did SonarQube report any cohesion or coupling problem in bikeshare-poor? Quote the rule and the message if it did. 
ANS. SonarQube reported no rule named cohesion or coupling. The closest finding is java:S2386, “Make this member “protected””, five times at Main.java:8-12. It points at the public static arrays, which are the shared data that the other methods change directly, so it is related to common coupling in the course terms. java:S3776, “Refactor this method to reduce its Cognitive Complexity from 33 to the 15 allowed”, at line 40 shows that handle is too complex, which matches the poor cohesion of a method that rents and returns in one body. But SonarQube does not name the course terms, and it did not report the boolean flag (control coupling) or the int member type codes (connascence of convention).

4.2 Did the AI review tell the two versions apart? Give one finding for bikeshare-poor that does not appear for bikeshare-good. 
ANS. Yes. In bikeshare-poor the AI found 8 problems in Check 2, and in bikeshare-good it found 5, all marked Note. One finding only for poor is Main.java:8-12: five public static fields that all methods share, which is common coupling. Nothing like it appears for good, where every field is private.

4.3 Which version would you rather extend with a new member type, such as a visitor who pays 4 baht per minute, and why?
ANS.I would extend bikeshare-good. A visitor who pays 4 baht per minute needs one new line in the enum MemberType, such as VISITOR(4), and feeFor works without a change. In bikeshare-poor I would have to change the int codes and the ternary operators at Main.java:43 and 74, and I could easily miss one of them.

## Task 5. Reflection

5.1 What does SonarQube check, and what does it not check? Answer with examples from this lab.

5.2 When should you not trust the AI review? Give an example from this lab.

5.3 What will you do differently in Project Phase 2 after this lab?

## AI usage declaration

| AI tool and exact model | Purpose | Files or parts that it produced or changed | How you checked its output |
| --- | --- | --- | --- |
| | | | |
