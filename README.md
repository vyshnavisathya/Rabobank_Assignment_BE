Customer Statement Validator

How to run the application:<br>
    > Download the project as a jar or clone the GITHUB repo<br>
    > Import as existing Maven application to the IDE<br>
    > run the file RabobankCustomerStatementProcessorApplication as a java application<br>
    > in a browser navigate to "http://localhost:8080/processCustomerStatement"<br><br>

Outputs Expected:<br>
    > The validated OK results will end up as JSON in the browser <br>
    > The error records if any would up in the class path as csv file with name FailedRecords_{filename_fileformat}.csv<br>
    > Files not being csv or xml would end up response code 500 and the name of the file would be displayed in the browser<br><br>

Assumptions:<br>
    > The file to the processed should be found in the input folder<br>
    > Any number of files can be put in the folder but the format should be csv or xml<br>
    > File size should not be more than 2 MB else it would end up as invalid file whose name would be displayed in the browser<br>
