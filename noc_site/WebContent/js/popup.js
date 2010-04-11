var popup_div;
var inputName;

 function ensurePopupPrepared(){
	 if(popup_div == undefined){
		 var body = document.getElementsByTagName("body")[0];
		 popup_div = document.createElement( "div" );
		 popup_div.id = "popup";
		 popup_div.className  = 'popup';
		 popup_div.onclick =cancel;
         body.appendChild(popup_div);
	 }
 }



function getHTML(item,src)
{
	var myAjax = new Ajax.Request(src,
			{method:"get",
		     onSuccess:function(response){
			     	item.innerHTML = response.responseText;
			     	popup_div.style.visibility="visible";   
		     },
		     onFailure:function(response){
			     	item.innerHTML = "ERROR";
		     }
     		});
}



function selectItem(item,targetName,href){
	ensurePopupPrepared();    	    	
    inputName = targetName;        
    getHTML(popup_div,href);
    //mask.style.display="block";
    popup_div.style.top = item.offsetTop;
    popup_div.style.left = item.offsetLeft;     
}  
function returnValue(value){
	
    $(inputName).value = value;     
    //alert(value);
    popup_div.style.visibility='hidden';
	$(inputName).fire();
}

function cancel(){
	//popup.style.display='none';
	popup_div.style.visibility='hidden';
}