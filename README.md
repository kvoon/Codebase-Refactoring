## Description
This project was a refactoring assignment, wherein we were provided with a codebase containing several issues outlined below. The primary objective of the assignment was to address and resolve these issues by applying a variety of design patterns and techniques taught during the course.

### Coding Style: Google Java Style 
*Only used in parts of the code that were altered. The original codebase that was left untouched may not follow the selected coding style.*

# Issues Listed and Suggested Design Patterns

## Issue 1:  
The system uses a LOT of RAM. Analysis has indicated this is due to the Report
class, which stores a lot of data. CPA would like you to solve this RAM issue
somehow, without breaking the existing use of the Report interface. ReportImpl
has been included in your module scope to assist with this, but ReportDatabase
is a fake façade on a remote database that you cannot change.

### Solved using Fly-weight pattern
Assumptions made: Assumed that the RAM issues were centered around the reports package.
Ram usage was checked when loading the list of reports. 

Done mostly in the `reports` package. 

## 2. Issue 2:  
There are several types of accounting service Orders. The current solution for
these orders is to create a new class for each type (based on work type e.g.audits or day-to-day work, whether the order is for priority client, and whether the
order is a one-off or regularly scheduled work). The full system has 66 * 2 * 2 of
these classes (264 order classes!), with 8 of these (2*2*2) provided to you as an
example – CPA would like you to find a way to reduce this class load without
breaking the existing Order interface.

### Solved with a Bridge Pattern
Also incorporated some other patterns such as strategy and factory. 
Assumptions: it was permissible to not use the OrderScheduled interface. It was just left alone and 
not edited or removed. However, Order interface was still used. 

Done in the `ordering` package. 

## 3. Issue 3:  
The current method of handling client contact methods is quite bulky – CPA would like you to streamline this somehow.

### Solved using the Chain of Responsibility pattern
*note: has been updated to handle dynamic switching between handlers instead of hardcoded
into a fixed chain*

Done in the `contacts` package 

## 4. Issue 4:   
Any time Clients are loaded from the database, the system lags for a long time.
The database issues themselves have been deemed too expensive to fix, but
perhaps you can partially mitigate this with the software somehow?

### Solved using Lazy load - lazy initialization 

Done in the `ClientImpl` class.  

## 5. Issue 5:   
Because the Report object captures data without any consistent primary key, and
because people have duplicated object names and versions, any time reports
need to be compared for equality we have to remember to check many fields.
CPA would like you to make this process simpler.

### Attempted to solve using Value Object
*note: Labelled as 'attempted to solve', as I used the report name as the key (i.e. not reliable as duplicates are possible). Missed the note in the Report interface 
until it was a bit too late.* 

Done mostly in the `ReportImpl` class 

## 6. Issue 6:   
The Order creation process involves a lot of slow database operations. CPA
would like you to simplify this process (especially the database lag while the
employee is entering data) without breaking the Order interface

**Attempted**:
 Attempted to apply Unit of Work design pattern. But was unable to finish it in time. 

## 7. Issue 7:   
The current system is mostly single-threaded. There has been some work on the
database side to allow multithreading, but as yet the FEAA module does not have
any threading besides the main one. CPA would like you to use multithreading to
allow employees to use the system for other things while slow database
processes happen in the background.

**Suggested : Concurrency** - did not attempt. 

## Acknowledgements
*  Chain of responsibility pattern tutorial, retrieved from: 
https://www.geeksforgeeks.org/chain-responsibility-design-pattern/ 

*  *SOFT3202 Lecture 7* (Lazy Initialization) 

*  *SOFT3202 Tutorial & Homework feedback* - Bridge, Flyweight, Value Object

* I would also like to acknowledge all the useful hints from my peers and the teaching staff on our cohort's ed forums (internal student discussion board).  
