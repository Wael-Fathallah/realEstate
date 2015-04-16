<?php   
      require_once('connect.php'); 
  
      mysql_select_db($database_localhost,$con);  
      $query_search = "SELECT * FROM personnes";  
      $query_exec = mysql_query($query_search) or die(mysql_error());  
      
if($query_exec!=null){
      $xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
      $root_element = "people"; 
      $xml .= "<$root_element>";
	while($result_array = mysql_fetch_assoc($query_exec))
     {
      $xml .= "<person>";
 
      foreach($result_array as $key => $value)
      {
         //$key holds the table column name
         $xml .= "<$key>";
 
         //embed the SQL data in a CDATA element to avoid XML entity issues
         $xml .= "<![CDATA[$value]]>"; 
 
         //and close the element
         $xml .= "</$key>";
      }
 
      $xml.="</person>";
   }
//close the root element
$xml .= "</$root_element>";
 
//send the xml header to the browser
header ("Content-Type:text/xml"); 
 
//output the XML data
echo $xml;
 }  
 ?>  