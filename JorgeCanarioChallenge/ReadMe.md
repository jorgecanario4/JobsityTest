# Read Me
	
### Configuration instructions:

Once installed no configuration is needed.

### Installation instructions:

To run this code, the followings need to be installed depending on your operative system.

- Java:
	- [Windows](https://java.com/en/download/help/windows_manual_download.xml)
	- [Mac](https://java.com/en/download/help/mac_install.xml)
	- [Linux](https://java.com/en/download/help/linux_install.xml)
	- [Solaris](https://java.com/en/download/help/solaris_install.xml)
	
- [Java SE 16.0.2](https://www.oracle.com/java/technologies/javase-downloads.html)

- [Maven](https://maven.apache.org/download.cgi)
	- Installation instructions: [here](https://www.baeldung.com/install-maven-on-windows-linux-mac)

- [Mozilla Firefox](https://www.mozilla.org/en-US/firefox/download/thanks/)

- [Google Chrome](https://www.google.com/chrome/?brand=BNSD&gclid=Cj0KCQjw-NaJBhDsARIsAAja6dMMi5e26GEsOsBpuZxsg7tuW3PG6iDpAameNprLb_r15VFOLtRdLXoaAhl2EALw_wcB&gclsrc=aw.ds)

- Download only if your OS is Windows 10 : [Internet Explorer 11](https://www.microsoft.com/en-us/download/confirmation.aspx?id=40907) **(OPTIONAL)**

- Download only if your OS is MacOS: [Apple Safari](https://support.apple.com/en-us/HT204416) **(OPTIONAL)**

> **Note:** there is a Safari for Windows but I wouldn't recommend as Apple officially is not supporting Safari for Windows. If you find some web that offers
> Safari for Windows, there's a high risk of downloading spyware, ransomwares, worm and trojans. We're better off running with other browsers (no worries code will not break because of missing a browser)


### Operating instructions:

To execute the test project:

- Apple Safari browser need to have checked the 'Allow Remote Automation' check before. To do so:

>
> 1. If you haven’t already done so, make the Develop menu available. Choose Safari > Preferences, and on the Advanced tab, select “Show Develop menu in menu bar.” 
> 2. Choose Develop > Allow Remote Automation.
>

- Open Terminal (if Linux or Mac OS) or Command prompt (if Windows)
- Move to the path of the project: `cd <project's folder location>`
- Run command: `mvn clean test`

Once executed, you can find report:

- **Test Run Report HTML** as `emailable-report.html` in: `./target/surefire-reports/`
- **Test Run Report PDF** as `emailable-report-<current date in following format: yy-MM-dd-hhmmssS >.pdf` in: `./target/surefire-reports/`
- **Test Run Report Interface** as `index.html` in: `./target/surefire-reports/`


### A file manifest (list of files included):
- **./doc/index.html** contains the Javadoc html with all the information of the classes' variables, methods and tests.
- **testng.xml** contains the specifications of the test execution i.e. execution order, browsers, listeners, etc.
- **pom.xml** contains all the information regarding the dependencies of the project.
- **./src/test/java** inside this folder are all the source code of the project.
- **./src/test/resources/References/indexPagePixelPerfectBaseline.jpg**  is the reference design given to compare current index page.
- **./src/test/resources/BrowserDriver** folder contains all the drivers of the browser that are going to be used for the execution.
- **./src/test/resources/TestingFiles/UploadTestFile.txt** is the test file that is going to be used to upload when necessary.


### Copyright and licensing information:

	No rights reserved

### Contact information for the distributor or programmer:

	Author: Jorge Canario / Email: jorgeic.767@gmail.com

### Known bugs:

- `mvn site` command not working for this project. Maven site is not generated due to some issue with TestNG annotations. For some reason the annotation notation generates an error when
trying to generate the maven site html.

### Troubleshooting:


