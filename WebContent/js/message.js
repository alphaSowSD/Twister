function Message(id, id_mes, auteur, texte, date) {
    this.id_message = id_mes;
    this.id = id;
    this.auteur = auteur;
    this.text = texte;
    this.date = date;
   /* if (comments == undefined) {
        comments = [];
        nbComments = 0;
    }
    this.comments = comments;
    this.nbComments = nbComments;*/
    //this.likes = likes;
    this.likes = 0;
}

 Message.prototype.getHtml = function (){
        msg = "<div class=\"msg\"> <div class=\"text_msg\"> <p>"+this.text+"</p></div>";
        
        if(this.auteur == env.id){
        	msg += "<div action=\"javascript:(function(){return;})()\" onClick=javascript:deleteMessage('"+this.id_message+"') class=\"trash\"> <img src=\"img/trash.png\"\></img> </div>";
        }

        msg += "<div class=\"auteur_msg\"> posté par : "+this.auteur+"</div>\
            <div class=\"date_msg\"> le "+this.date+" </div>\
            <div class=\"like\"> <span>"+this.likes+"</span> <img onClick=\"javascript:like()\" src=\"img/like.png\"></img></div>\
            <div class=\"dislike\"> <span>"+this.likes+"</span> <img onClick=\"javascript:dislike()\" src=\"img/dislike.png\"></img></div>\
            <div class=\"fav\"> <span>"+this.likes+"</span> <img onClick=\"javascript:fav()\" src=\"img/fav.png\"></img></div>\
            <div class=\"commentaire\"><div class=\"zone_commentaire\">\
                    <form id=\"new_comment\" method=\"GET\" action=\"javascript:(function(){return;})()\" onSubmit=\"javascript:nouveauCommentaire(this)\">\
                        <textarea name=\"new_comment\" form=\"new_comment\" placeholder=\"Réagir\"></textarea>\
                        <input class=\"poster\" type=\"submit\" value=\"Commenter\"/>\
                    </form>\
                </div>\
            <div class=\"list_commentaire\">\
            </div>\
            </div>\
            </div>";

        return msg;
 }


function printMessage(){
    s = "";

    for (let item of env.msgs){
    	s+= item.getHtml();
    }

    $("#messages").html(s);
}


function revival(key, value){
    if(value.id != undefined){
        var c = new Message(value.id, value.id_message, value.user, value.content, value.date);
        return c;
    }else if(value.id_commentaire != undefined){
        var c = new Comments(value.id, value.auteur, value.text, value.date);
        return c;
    }else{
        return value;
    }
}

function completeMessagesReponse(rep) {
    if (rep.error == undefined) {
        var listeMessages = JSON.parse(rep,revival);
        env.nb_msg = rep.nbMessages;     
        
        // Mettre à jour les messages.
        env.msgs.clear(); 
        
        for (var i=0; i < listeMessages.messages.length; i++) {
            
        	env.msgs.add(listeMessages.messages[i]); 
            
            if(listeMessages.messages[i].id>env.maxId){
                env.maxId=listeMessages.messages[i].id;
            }
            if(env.minId < 0 || listeMessages.messages[i].id < env.minId){
                env.minId = listeMessages.messages[i].id;
            }
        } 
    } else {
        func_erreur(rep.message);
    }
    
    printMessage();
}



function completeMessage(){
    var users = env.id+"";

    for (let item of env.follows) users+=" "+item;

    if(!noConnection){
        $.ajax({
            type : "GET",
            url : "message/GetMessages",
           // data : "key="+env.key+"&id_users="+env.query+"&from="+env.fromId+"&id_max="+env.minId+"&id_min=-1&nb==0",
            data : "key="+env.key+"&id_users="+users,
            success : function (rep){
                completeMessagesReponse(rep);
            },
            error : function (jqXHR, textStatus, errorT){
                alert(textStatus);
            },
        });
    }
}

function listMessageUser(){
    if(!noConnection){
        $.ajax({
            type : "GET",
            url : "message/GetMessages",
            data : "key="+env.key+"&id_users="+env.id,
            dataType : "JSON",
            success : function (rep){
               reponseMessageUser(rep);
               completeMessage();
            },
            error : function (jqXHR, textStatus, errorT){
                alert(textStatus);
            },
        });
    }
}

function reponseMessageUser(rep){
    if(!noConnection){
        env.nb_msg = rep.nbMessages;
        $("#nb_msg").html(env.nb_msg);
    }
}

function nouveauMessage(formulaire) {

    var texte = formulaire.new_message.value;

    if(!noConnection){
         $.ajax({
            type:"GET",
            url: "message/AddMessage",
            data:"key=" + env.key + "&content=" + texte,
            datatype: "json",
            success: function(rep) {
                nouveauMessageReponse(rep);
            },
            error : function (jqXHR, textStatus, errorT){
                alert(textStatus);
            },
        });
    }
   
}


function nouveauMessageReponse(rep){
    if((rep != undefined) && (rep.error == undefined)){
    	listMessageUser();
    }else{
        alert(rep.error);
    }
}


function deleteMessage(id){
	 if(!noConnection){
         $.ajax({
            type:"GET",
            url: "message/DeleteMessage",
            data:"key=" + env.key + "&idmessage="+id,
            datatype: "json",
            success: function(rep) {
            	listMessageUser();
                reponseDeleteMessage(rep);
            },
            error : function (jqXHR, textStatus, errorT){
                alert(textStatus);
            },
        });
    }
}


function reponseDeleteMessage(rep){
	listMessageUser();
}

function nouveauCommentaire(form){

}