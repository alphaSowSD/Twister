

function init(){
	env = new Object();
	noConnection = true;
}


function makeMainPanel(){

	if(noConnection){	
		env.fromId = -1;
	    env.query = undefined;
	    env.messages = [];
	    env.minId = Infinity;
		env.maxId = -Infinity;
	}
	/*var s = "<head id=\"top\">"

	if(env.fromId < 0){
		s+="<div id=\"title\" > Actualiter </div>"
	}
	else{
		s+= "<div id =\"title\"> Pas de login "+env.fromLogin+"<div class = \"add\" > <img src = \" Images/add.png\" title=\"Suivre\" onClick = javascript:follow()> </div></div>";
	}
	s+= "</div>"
		"<div id = \"connect\">"
		"<span id = \"login \" onClick = \"javascript\">"*/
	$("body").load("html/page_principale.html");
}

// TD - Map Reduce 

// Question 1 :

function map(){
	var text = this.text;
	var words = text.match("/\w+/g");
	var tf = {};
	for(var i=0; i< words.length; i++){
		if(tf[words[i]] == null){
			tf[words[i]] = 1;
		}else{
			tf[words[i]] += 1;
		}
	}

	for(w in tf){
		enit(w,1);
	}
}

function reduce(key, walues){
	return values.length;
}

// Question 2 :

/* " Jean fait un projet"
	"Jean mange un sandwich" 

{"jean" : [(0,0) , (1,0)]} 
 "fait" : [(0,1)]; 
 "un" : [(0,2)(0,2)]
    .
 	.
    .

m = function {
	var text = this.text;
	var id = this.id;
	var words = text.match ("/\w+/g");
	var tf = {};
	for(var i=0; words.length; i++){
		if(tf[words[i]] == null){
			tf[words[i]] +=1;
		}else{
			enit(tf[words[i]] +=1);
		}
	}

	for(w in tf){
		var ret = {};
		ret[id] = tf[w];
		enit(w,ret);
	}
}

r = function (key, values){
	var ret = {};
	for(var i=0; i<values.length; i++){
		for(var d in values[i]){
			ret[d] = values[i][d];
		}
	}

	return ret;
}

f = function(k,v){
	var df = Object.keys(v);
	
	for(d in v){
		v[d] = v[d]*Math.log(N,df);
	}

	return v;
}

db.runCommand({
	mapreduce : " x", map : 
})*/