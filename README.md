# camel-bindy-poc

.setHeader("propCount", simple("${bean:incrementalService?method=addNumber(property.RowCount)}"))  //number Increment by given num

Service Bean to Increment by given number.