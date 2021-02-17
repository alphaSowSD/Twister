function listFollower(){
	if(!noConnection){
		$.ajax({
            type : "GET",
            url : "friend/ListFriend",
            data : "cle="+env.key+"&login="+env.login,
            dataType : "JSON",
            success : function (rep){
            	listFollowerReponse(rep);
            },
            error : function (jqXHR, textStatus, errorT){
                alert(textStatus);
            },
        });
	}
}

function listFollowerReponse(rep){

	var h = "";

	if(rep.error == undefined){
		if(!noConnection){
			for(var i=0; i< rep.amis.length;i++){
				env.follows.add(rep.amis[i].idfriend);
			}
		}

		h+= "<h2 class=\"titre_info_profil\"> Abonnement </h2>\
            <span id=\"val_abonnement\">"+rep.amis.length+"</span>";

		$("#nb_abonnement").html(h);
	}	
}

function recommandation(){
	if(!noConnection){
		$.ajax({
            type : "GET",
            url : "friend/Recommandation",
            data : "key="+env.key,
            dataType : "JSON",
            success : function (rep){
            	recommandationReponse(rep);
            },
            error : function (jqXHR, textStatus, errorT){
                alert(textStatus);
            },
        });
	}

}

function recommandationReponse(rep){

	var s = "";

	if(rep.error == undefined){
		for(var i=0; i<rep.membres.length; i++){
			s+="<div class=\"recommand\">\
                        <div class=\"login_member\">\
                            <span>"+rep.membres[i].loginMembre+"</span>\
                        </div>";

            if(rep.membres[i].ami == true){
            	s+= "<div class=\"icon_following\" action=\"javascript:(function(){return;})()\" onClick=\"javascript:unfollow('"+rep.membres[i].loginMembre+ "', '"+rep.membres[i].idMembre+"')\">\
            			<img src=\"img/unfollow.png\"></img>";
            }else{
            	s+= "<div class=\"icon_following\" action=\"javascript:(function(){return;})()\" onClick=\"javascript:follow('"+rep.membres[i].loginMembre+"' , '"+rep.membres[i].idMembre+"')\">\
            			<img src=\"img/follow.png\"></img>";	
            }
                          
            s+="</div></div>";
		}

		$("#membres").html(s);
	}else{
        makeConnexionPanel();
    }
}


function follow(login,id){
	if(!noConnection){
		$.ajax({
            type : "GET",
            url : "friend/AddFriend",
            data : "key="+env.key+"&login="+login,
            datatype : "json",
            success : function (rep){
                reponseFollow(rep);
                listFollower();
            },
            error : function (jqXHR, textStatus, errorT){
                alert(textStatus);
            },
        });
	}
}

function unfollow(login,id){
	if(!noConnection){
		$.ajax({
            type : "GET",
            url : "friend/DeleteFriend",
            data : "cle="+env.key+"&login="+login,
            datatype : "json",
            success : function (rep){
                reponseUnfollow(rep);
                listFollower();
            },
            error : function (jqXHR, textStatus, errorT){
                alert(textStatus);
            },
        });
	}
}

function reponseFollow(rep){

	if(rep.erreur == undefined){
		recommandation();

	}
	else{
		alert("erreur");
	}
}

function reponseUnfollow(rep){

	if(rep.erreur == undefined){
		recommandation();
	}
	else{
		alert("erreur");
	}
}