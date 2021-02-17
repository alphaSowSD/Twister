function developpeMessage(id){
    var m = env.msgs[id];
    var el = $('#message_'+id+".comments");

    for(var i=0; i < m.length;i++){
        var c = m.comments[i];
        el.append(c.getHTML());
    }

    el = $('#message_'+id+".new_comments");
    el.append("<form name=\"new_comment_form\" id=\"new_comment_form\" action=\"javascript:new_comments("+id+")\"/>");

    $("#message_"+id+" img").replaceWith("<img src=\" \" onClick=\"javascript:replieMessage("+id+")\" />");
}

function replieMessage(id){

    var m = env.msgs[id]
    var el = $("#message_"+id+".comments");
    el.html(" ");
    $("#message_"+id+"img").replaceWith("<img src=\" \" onClick=\"javascript:developpeMessage("+id+")\" />");
}

function newComments(id){

    var text = $("#new_comment_form"+id).val();
    if(!noConnection){

    }else{
        newComment_reponse(id, JSON.stringify(newCommentaire(env.msgs[id].comments.length+1,{"id" : env.id, "login" : env.login}, text, new Date())));
    }
}

function newComment_reponse(id, rep){

    var con = JSON.parse(rep, revival);
    if((con != undefined && comm.erreur == undefined)){
        var el = $("message_"+id+".comments");
        el.append (con.getHTML());
        env.msgs[id].comments.push(con);
        if(noConnection){
            localdb[id]=env.msgs[id];
        }
    }else{
        alert(con.erreur);
    }
}

//8.3 
//from id à -1 
/*

min et max permet d'afficher les 10 plus récents par exemple 
minId = message le plus ancien


*/


//A la suite d'une publication on veut rafraichir avec le nouveau message en haut de la listMessage

function refreshMessage(){
 if(env.query!=undefined){
     return;
 }
 if(!noConnection){
     $.ajax({
         type : "GET",
         url : "message/GetMessages",
         data:"key="+env.key+"&query=''&from="+env.fromId+"&id_max=-1&id_min="+env.maxId+"&nb=1",
         datatype : "json",
         succes : function(rep){
             refreshMessageReponse(rep);
         },
         error : function (jqXHR, textStatus, errorT){
             alert(textStatus);
         },
     });  
 }
}

function refreshMessageReponse(rep){
 
 var tab = JSON.parse(rep, revival);

 if(tab.length != 0){
     for(var i=tab.length-1; i>=0; i--){
         var m = tab[i];
         $("#messages").prepend(m.getHTML());
         env.msgs[m.id] = m;

         if(m.id > env.maxId){
             env.maxId = m.id;
         }

         if (m.id < env.minId) {
             env.minId = m.id;
         }
     }
 }
 else {
     console.log(rep.message + ", ERROR_CODE: " + rep.errorcode);
     func_erreur(rep.message);
 }   
}


function like(){


}

function dislike(){

}

function defMess(){
    m1 = new Message(1,2,"blabla", "02/02/2018", "qsfqsfqsd", 10, 5);
    m2 = new Message(2,3,"blablQDSa", "02/02/2018", "qsfqsfqsd", 10, 5);
    m3 = new Message(3,1,"bQSFDlabla", "02/02/2018", "qsfqsfqsd", 10, 5);
    m4 = new Message(4,2,"blablQSDa", "02/02/2018", "qsfqsfqsd", 10, 5);
    localdb = [m1,m2,m3,m4];
}

defMess();
