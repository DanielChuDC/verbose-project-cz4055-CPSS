### Folder structure

The source code for java application is under `CPA4055Project`

The `test` directory hold the wave10 to wave100 and its output folder consists of the `BruteForceCorrelation` files.

The `CPA_part_1` and `CPA_part_2` for the data analytics parts.

```
├── CPA4055Project
│   ├── Readme.md
├── CPA_part_1.ipynb
├── CPA_part_2.ipynb
├── Readme.md
└── test
    ├── CPA4055Project-1.1.0.jar
    ├── output
    ├── wave10
    │   ├── CPA4055Project-1.1.0.jar
    │   ├── output
    │   └── waveform.csv
    ├── wave100
    │   ├── CPA4055Project-1.1.0.jar
    │   ├── output
    │   └── waveform.csv
    ├── wave20
    │   ├── CPA4055Project-1.1.0.jar
    │   ├── output
    │   └── waveform.csv
    ├── wave30
    │   ├── CPA4055Project-1.1.0.jar
    │   ├── output
    │   └── waveform.csv
    ├── wave40
    │   ├── CPA4055Project-1.1.0.jar
    │   ├── output
    │   └── waveform.csv
    ├── wave50
    │   ├── CPA4055Project-1.1.0.jar
    │   ├── output
    │   └── waveform.csv
    ├── wave60
    │   ├── CPA4055Project-1.1.0.jar
    │   ├── output
    │   └── waveform.csv
    ├── wave70
    │   ├── CPA4055Project-1.1.0.jar
    │   ├── output
    │   └── waveform.csv
    ├── wave80
    │   ├── CPA4055Project-1.1.0.jar
    │   ├── output
    │   └── waveform.csv
    ├── wave90
    │   ├── CPA4055Project-1.1.0.jar
    │   ├── output
    │   └── waveform.csv
    └── waveform.csv
```


### How to run this project

The output of fat jar will be generated in `./build/libs/CPA4055Project-1.1.0.jar`
Put the waveform file together with the fat jar.
Run the jar with command `java -jar CPA4055Project-1.1.0.jar`
```java

./gradlew fatjar
java -jar CPA4055Project-1.1.0.jar


```


### Technology used

This project built with gradle 8 and logback plugin used

### Analytics part

Using Python 3.10 with jupiter notebook feature

- CPA_part_1
- CPA_part_2


### Instruction from BlackBoard

Project (Implementation of Correlation Power Analysis)
Dear Students,
    This is the folder where you need to submit your project based on the Side-channel Attack presented in Lab 1+2. The project can be done in groups and a maximum of three students per group is allowed. In the project report, clearly state the details of the team members with name and matriculation numbers.

Please also note that this is the only project topic available for this course. So, all groups are expected to complete this project.
Project Abstract: Given a set of power traces (in a csv file format as captured in the lab), you are required to implement Correlation Power Analysis (CPA) on the given traces to extract the key. You can use any programming language or framework of your choice for the same (Python, MATLAB, C, C++, Java, R etc.)
Project Deliverables:
1. Report:
a. A brief explanation of the CPA technique.
b. Explanation of your implementation of the CPA.
c. The experimental results section with the following plots:
-- Plot-1: For 100 traces, plot the correlation of all possible key bytes and highlight the correlation of the correct key byte in red.
Instructions for Plot-1:
Let us say the matrix you built is M (its size will be 100 x 256). Let us say the power traces you captured in the lab is T (size - 100 x 2500). So, you take every column of M and correlate with every column of T and get correlation value for each column combination. Lets say your correlation matrix is C. So, the size of C is 256 x 2500. Each row in this corresponds to the possible value of the key byte. Each column corresponds to the time index on the power trace.


> Let us first consider the first byte of the key. we denote it as K0. So, you would have constructed the correlation matrix for recovering byte K0. We denote it as C. You take every row of C and identify the highest correlation value in that row (positive or negative, doesnt matter, take absolute value). Let it be x. 

So, this is the highest value of correlation iif the value of K0 is 0x00.  Similarly for K0 = 0x01, you take the highest correlation value and so on until key byte = 0xFF. You can simply plot all these 256 values. 

So, on x-axis, you have value of the key byte and on y-axis, you have correlation value. You will see that the correct key byte has the highest correlation value. You can do this for all the 16 bytes.



---

-- Plot-2: Plot for "Correlation of the correct key byte vs number of traces". Let the number of traces run from 10 to 100 in steps of 10. This plot need to be shown for all the 16 bytes of the key.


### Instructions for Plot-2:
> You just need to implement CPA with different number of traces. For example, implement CPA with 10 traces. now, for key byte 0. Now, you do the same as done for Plot 1. You get the correlation values for all values of the key byte. So, you have 256 points. On the plot, x-axis is number of traces and y-axis is correlation. So, you just plot all the 256 points at x = 10. Now, repeat the same for increasing number of traces (20, 30, ...., 100). You will see that the correlation value for the correct key byte will start emerging as you increase the number of traces. The same can then be done for all the 16 key bytes. You can see that for all the bytes, the correct value emerges
d. A literature survey of one page on possible countermeasures against side-channel attacks is also need to be presented.


2. Code:
The actual code and the corresponding executable needs to be attached in zip file (preferably using a README file that says how to execute the program) and has to be uploaded on NTU Learn. If you do not have the waveform data you have collected from the lab, you can also utilize the sample waveform file in the Lab-1 folder. You can run your CPA analysis on that sample waveform.


> IMPORTANT INFORMATION: In the event that you are not able to submit the project within the deadline due to difficulties in implementing the CPA algorithm, we will be providing you a pseudo code of the CPA algorithm as additional help. However, seeking additional help will mean deduction of 5 points from each of the group member (So, you will only receive a maximum of 15 out of the 20 possible points).
Please feel free to write to us or setup personal meetings with us to discuss details of the CPA algorithm if you have any doubts or queries. We are always ready to help!!!
PRASANNA RAVI (PRASANNA.RAVI@ntu.edu.sg)
NAINA GUPTA (naina003@e.ntu.edu.sg)
More details on the project are present in slide 11 in the "Report and Project Details" slides within the Lab-2 folder.
Project Submission Deadline: 25th April, 2023.
Please let us know personally if you require any more details regarding the same.
