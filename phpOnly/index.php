
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
	
		$totalPrice = 0;
		
		$page = 1;	//the page of json data being acquired
		$json;
		$obj;
		$objArr =array();
		
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
		*/
		$json = file_get_contents('http://shopicruit.myshopify.com/products.json?page='.$page);
		$obj = json_decode($json);
		while(!empty($obj->products)){
			$objArr[] = $obj;
			$page++;
			$json = file_get_contents('http://shopicruit.myshopify.com/products.json?page='.$page);
			$obj = json_decode($json);			
		}
		
		
		$table = "";
		//print json data in table format
		echo '<table>';
		echo '	<th>Product Name</th>
				<th>Product Variant</th>
				<th>Price</th>';
		foreach($objArr as $productPage){
			foreach($productPage->products as $product){
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
		echo '</table>';
		echo "total price: ". $totalPrice;
		
		
		
		?>
	
	</p>
		</div>
	</body>
</html>