1)Chromedriver version 73.0 is included by default  . Clone this project and this should run the chrome. In case you are running a higher version of chrome download chrome from http://chromedriver.chromium.org/downloads and place it in parent folder  
This will run on chrome browser only.

2) Once you create a gmail id go to this URL and enable allow access to less secure apps https://myaccount.google.com/lesssecureapps?pli=1

3) You will have to use gmail account only for running the automation. I have for now used my account but I will be deleting it for security reasons. So please create your own account and use.

4) In the email.properties file change email.username and email.password to match gmail account creds you will use.Please use dedicated 
   account for running tests. As OTP is always got from the most recent email.
   
5)  I have added a user.properties file and you can set the cipherhutuser id and cipher hut password to login to app. 

6) For balance comparison on the funds page, ActualBalance.csv is generated at run time with actual data and is compared with ExpectedBalance.csv . Please specify the expected data in  ExpectedBalance.csv file, you can specify all lines or just the line you want to verify based on your need.For reference I have created ExpectedBalance.csv and one ExpectedBalance2.csv. 
Always the filename with expected data must be ExpectedBalance.csv.If ExpectedBalance.csv is not present then the test checkBalance will fail.

7) You can delete generated files(csv pairs) having file name BTC.csv, CBNX.csv, EUR.csv, TRY.csv and USDT.csv and run the project this will create the files as part of the test run. 
