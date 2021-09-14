function Deletecomment(comment_id) {

}
function DeleteReplay(replay_id) {

}
function choose(x){
    switch (x){
        case 0:
            document.getElementById("comment").setAttribute("style","display:block")
            document.getElementById("replay").setAttribute("style","display:none")
            break
        case 1:
            document.getElementById("replay").setAttribute("style","display:block")
            document.getElementById("comment").setAttribute("style","display:none")
            break
    }
}