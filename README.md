# jpmorgan_technical_test
technical test for JP Morgan

INTRODUCTION
------------

This work is the completion of the 'Daily Trade Reporting Engine' technical test. This work has been carried out 
exclusively by myself (Bill Vance).

Although this task necessarily deviates from reality (e.g. all no persistence, report printed to console), the system architecture
reflects a more real-world structure (such a DAO class for the trading instructions, in the likelihood that these would be persisted in reality).

The package structure reflects the nature of the task, e.g. it should come as no surprise that the 'reporting' package contains the actual report class,
supporting utility class, etc. The structure intends, in part, to help clarify the purpose of the software and should lend itself to easily apprehend
an overview of the project.

Regarding testing, I am satisfied with the level of test coverage that has been provided. However, given more time, I would have cleaned up
the code in some of these tests; there is some duplication (particularly in the setting up of initial conditions) in there that is not best practice - I just wanted you to know that I realise
this.

ASSUMPTIONS/FURTHER QUESTIONS
------------------------------

I found the instructions to be straigtforward, but if there had been an opportunity to ask for further info, I would have raised the following
questions:

- The Report says to display daily trade totals, per day. It didn't spcecify the display order. What is it? For this exercise, I took the liberty of dispaying them
    from most recent to oldest.  
- The report says to rank entities in terms of max trade value for a single instruction. It doesn't say to do this by day, as is the
    case for the daily trade totals. I took the instructions at face value here, but would have clarified this in reality.
- Also regarding the max trade value for a single instruction, the specification didn't say what to do in the case that some entities
    were of equal max trade value. The current implementation will assign a different rank (e.g. 1 and 2) to two entities with the
    same trade value. The ranking information would be clearer if it weres structured as follows (allowing for multiple entities in
    a single ranking position):
        1) DXM
        2) ARN, DDY, NSU
        3) PNB

    I didn't implement this preferred solution, but would have definitely asked for clarification in a real situation.

BUILDING AND RUNNING
-----------------------

This is a maven project, therefore you must have maven installed in order to compile it. Also, you must have java 1.8.

The steps to compile and run the report are all done from command line, and from the trade_report_exercise directory.
The second step listed below will run all unit tests, and you can see that they pass.
The third step will cause the program to run, and output the report to the console.

1) mvn clean
2) mvn package
3) java -jar target\codetest-0.0.1-SNAPSHOT.jar



