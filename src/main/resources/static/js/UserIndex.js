function choose(chose) {
    if(chose==1){
        document.getElementById("post_blog").setAttribute("style","display:block")
        document.getElementById("collection_blog").setAttribute("style","display:none")
    }
    else if(chose==2){
        document.getElementById("collection_blog").setAttribute("style","display:block")
        document.getElementById("post_blog").setAttribute("style","display:none")
    }
}