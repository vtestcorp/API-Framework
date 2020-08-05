# API Framework


## Introduction
 
API Framework that does not require you to write code for almost all APIS. 

## Requirements

* Java 1.8
* Install maven[In POM.xml, we have added all the dependencies, required for running this API automated suites]

## Getting Started

* Clone repository.
* For running existing example(Excel sheet) follow below steps.
* Generate key from google place [Click here](https://developers.google.com/places/web-service/get-api-key) and click on **GET A KEY** button:![alt text](https://Manjeet-vtestcorp.tinytake.com/media/da343a?filename=1596620271070_Google+API_Location_1596620271.png)

* Go to src/test/resources/ExcelData
* Open Excel **googleplace** sheet and modify $$$$$$$ from parameters column to above generated key.

For more detail refer guidelines.

## Optional Configuration[Google drive support]

* Delete client_secret.json from src/main/resources.
* Turn on the [Drive api](https://developers.google.com/drive/v2/web/quickstart/java) (Step 1 only)
* Move client_secret.json to src/main/resources and give file name **client_secret.json**  
* Go to src/test/resources/ExcelData
* Go to src/main/java/com/framework/constants >> Constants.java
* Search EXCELFILEPATH variable and change to above **excel id** and save file. 
* Modify/check your **Application Name** in com.framework.constants >> Constants.java >> APPLICATIONNAME(As per ongoing project)


## Operators

Number|Operator | Example | Description

------|---------| ------- | -----------

 1| #  # |#Sheet Name.Test Id.Path# | Single dynamic value replacement.
 
 2| @  @ |@Sheet Name.Test Id.Path[0]@ | Single dynamic value replacement from dynamic list.
 
 3| @  @ |@Sheet Name.Test Id.Path@ | List value replacement for **Assert response** only.
 
	
## Guidelines

### Run mode for TestFlow: 

Runmode helps for running particular TestFlow.

Test Id | Test Mode | Test Flow Name | Test Case Name
---     | ---       | ---            | ---
1       | Yes       | Get Token for users | getAccessTokenForOpsUser
2       |           |                     | getAccessTokenForWMGJ|
3       | No        |Get To Be Paid Package| getToBePaidPackageGJLP |  


### Extract response value:

Extract values from the "**API response**" and use for the next **"Test Cases or Flows**.

Number|Function |Test Method and json path |Result

------|---------|--------------------------|------

1|extractString|**extractString**:$.responseData.X-Authorization-Token|The X-Authorization-Token Value

2|extractNumber|**extractNumber**:$.responseData.packages[0].amount|Amount First From Packages. 

3|extractLong|**extractLong**:$.responseData.payment.lpTransaction.transactionDate |The Transaction Date |Value(Epoch form)

4|extractBoolean|**extractBoolean**:$.status |The Status Value

5|extractStringList|**extractStringList**:$.responseData.payments[*].lpTransaction.status |All Staus from Payments.

6|extractLongList |**extractLongList**:$.responseData[*].createdOn|All Created On Dates(Epoch form)


### Extract dynamic value for TestCases.

Extract dynamic values use for **dynamic value replacement** from the "**API response**" for the below column:

* Test Url
* Test Input Json
* Test Headers
* Test Parameters
* Test Assert Response

For Example

Number|Column Name | Syntax | Example | Result

------|------------|--------|---------|-------

1|All above column|**#Sheet Name.Test Id.path#**|#googleplace.1.lng#| Get 1st Test Id Value from googleplace sheet. 

2|All above column|**@Sheet Name.Test Id.Path[0]@**|@manifest.14.id[0]@|Get 1st Value from List of 14 Test case.

3|Test Assert Response|**@Sheet Name.Test Id.Path@**|@googleplace.24.status@ |Get All vallue from List of 24 Test Case.

**Compare more than 2** value in Test Assert Response use "**;**" 

For e.g
 
googleplace.15.reconciliationStatus#,#googleplace.14.reconciliationStatus#**;**#googleplace.14.reconciliationStatus#,to_be_paid
	

### Schema validation for response.

* Add your schema under src/test/resources

Test Schema Name |

-------------------------|

accesstokensuccess.json|

####  Epoch Date for parameters:

**Extract dynamic values for below data**:

* startdatetoday
* enddatetoday
* startdateyesterday
* enddateyesterday
* startdatethisweek
* startdatelastsevendays
* startdatethismonth
* startdatelastmonth
* enddatelastmonth
* startdatecustomrange

For Example

* **Test Parameters**: ?createdFrom=#epoch.0.startdatetoday#&createdTo=#epoch.0.enddatetoday#

## Run

* mvn clean compile test **or** Open testng.xml file and Run As TestNG Suite.
* If user do **Optional configuration** a new window will generate and select previously used email address.
* Click on **Allow** button.

## Reports

* Open Reports folder after running framework.
* Report will generate for each TestFlow.


## Limitation

If user retrieves **List of value** from response for e.g @googleplace.24.status@ and user want to replace all list value to URL,Parameters,Headers is not possible.
 
**Alternate**:User can replace with @googleplace.24.status[0]@ or @googleplace.24.status[1]@