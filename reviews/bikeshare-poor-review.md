1. Files read: bikeshare-poor/src/main/java/bikeshare/Main.java

2. Findings

| No. | Check | Code location (file:line) | Finding | Severity | Suggested fix |
| --- | --- | --- | --- | --- | --- |
| 1 | Check 1 | Main.java:40-88 | handle is about 50 lines with deeply nested branches and loops, which is hard to read. | Should fix | Split it into rentBike and returnBike. |
| 2 | Check 1 | Main.java:43 | A nested ternary chooses the member type. | Should fix | Replace it with an enum and a lookup. |
| 3 | Check 1 | Main.java:49,55,72 | The same loop over the bikes is written three times. | Note | Move the search into one helper method. |
| 4 | Check 1 | Main.java:43,74 | The numbers 0, 1, 2 and 3 are written in the code without names. | Note | Name them as constants or use an enum. |
| 5 | Check 1 | Main.java:85-87 | Missing words are handled by catching ArrayIndexOutOfBoundsException instead of checking the length. | Note | Check w.length before reading w[1] to w[3]. |
| 6 | Check 2 | Main.java:40-88, 23 | handle has logical cohesion, because the boolean rent chooses what it does. The call at line 23 is control coupling. | Must fix | Use two methods, one for rent and one for return. |
| 7 | Check 2 | Main.java:8-12 | Five fields are public static, so any code can change them. | Must fix | Make them private and put them in a Bike class. |
| 8 | Check 2 | Main.java:8-12, 40-94 | Common coupling. All methods share the same global arrays. | Should fix | Pass the data that a method needs, or keep it inside an object. |
| 9 | Check 2 | Main.java:7 | The class holds the menu, the fee rules, the bike records and the file saving, so its class cohesion is weak. | Must fix | Move each job into its own class. |
| 10 | Check 2 | Main.java:12,43,74 | Connascence of convention. The int 1 means student and 2 means staff in several places. | Should fix | Use an enum MemberType. |
| 11 | Check 2 | Main.java:10,56,57 | Connascence of convention. The value -1 in bikeAt means that the bike is rented out. | Note | Store a status or a Bike object. |
| 12 | Check 2 | Main.java:9-12, 57-59, 75-76 | Connascence of position. Four parallel arrays must be read with the same index i. | Should fix | Use one Bike object that holds these values. |
| 13 | Check 2 | Main.java:43,44,66,67 | Connascence of position. w[2] means the member type in rent and the station in return. | Note | Parse the words into named values before use. |

Not found: unused imports, fields or parameters, empty blocks, resources that are never closed, and objects compared with == .

3. Number of findings
- Check 1 (code quality): 5
- Check 2 (cohesion, coupling and connascence): 8