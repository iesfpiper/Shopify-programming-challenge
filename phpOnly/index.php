<!DOCTYPE html>
<html>
	<head>
		<title>Shopify Programming Challenge</title>
	</head>
	<body>

		<div id="main">
			<h1 class="centered">Welcome to my solution page</h1>
			<p class="centered">
				Programming Challenge Solution (In development stage)
				<br />
				<?php
				
					//DEBUGGING VARIABLES
					$debug = true;
				
					$page = 1;	//the page of json data being acquired
					$json;
					$obj;
					$objArr =array();
			
					$table = "";
					$totalPrice = 0;
					
					
					/*get JSON data
					*	this section of the code assumes that the pages of data start with page n=1
					*	and that pages increment such that the next page is n+1
					*
					*	For each page, the json data is decoded
					*		the data is checked to see if the products object contains any data
					*			If the products object contains data than the entire object $obj is
					*			added to $objArr
					*		else
					*			If the products object is empty than I assume we have just passed
					*			the last page that contains data, stop searching for more data
					*	
					*	Note: looking for an elegent way to implement this pattern of 
					*		  (load file) -> loop[(check file)->load file)]
					*		  so that the code does not have to duplicate the load file source
					*/
					//get data from web server
					$json = file_get_contents('http://shopicruit.myshopify.com/products.json?page='.$page);
					$obj = json_decode($json);
					//check validity of data, repeat until first occurrance of invalid data
					while(!empty($obj->products)){
						$objArr[] = $obj;
						$page++;
						//get data from web server
						$json = file_get_contents('http://shopicruit.myshopify.com/products.json?page='.$page);
						$obj = json_decode($json);
					}
					
					//print json data in table format
					echo '<table>';
					echo '	<th>Product Name</th>
							<th>Product Variant</th>
							<th>Price</th>';
					foreach($objArr as $productPage){
						foreach($productPage->products as $product){
							if(strtolower($product->type) == "watch" or strtolower($product->type == "clock")){
								foreach($product->variants as $variant){
									echo '<tr>';
									$totalPrice += $variant->price;
									echo '<td>' . $product->title . '</td>';
									echo '<td>' . $variant->title . '</td>';
									echo '<td>$' . $variant->price . '</td>';
									echo '</tr>';
								}
							}
						}
					}
					echo '</table>';
					echo "total price: ". $totalPrice;
				?>
			</p>
		</div>
	</body>
</html>
