function show() {
    var box=document.getElementById("show")
    var p=JSON.parse(box.value)
    var KeyWord=""
    for (var o in p){
        var content = document.getElementById("content");
        var contents = content.innerHTML;
       var value = o;
       var values = contents.split(value);
       KeyWord+=value
       console.log(value)
       console.log(values)
       content.innerHTML = values.join('<span style="color:red;">' + value + '</span>');
    }
}