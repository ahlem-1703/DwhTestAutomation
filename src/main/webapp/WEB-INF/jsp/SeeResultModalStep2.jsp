<%--
  Created by IntelliJ IDEA.
  User: yassi
  Date: 31/05/2023
  Time: 16:34
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>See Result Modal</title>
</head>
<body>
<div class="modal fade" id="SeeResultQueryModalStep2" tabindex="-1"
     role="dialog" aria-labelledby="SeeResultQueryModalStep2"
     aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content" id="bodyModalSeeQueryStep2">

    </div>

  </div>
</div>
<script type="text/javascript">
    function SeeResultQueryModalFunctionStep2(){

        var html = '';

        var form = document.getElementById('formkpi'); // Replace 'yourFormId' with the actual ID of your form
        var formData = new FormData(form);
        fetch('/LaunchScriptTestResultStep2',{
            method:'POST',
            body: formData
        })
            .then(function (response) {
                if (response.ok) {
                    return response.json();
                } else {
                    throw new Error('Error: ' + response.statusText);
                }
            })
            .then(function(data) {
                // Process the response data
                var modal = document.getElementById('exampleModalLong2');
                var modalInstance = new bootstrap.Modal(modal);
                modalInstance.hide();
                html = renderHtml(data.columns,data.rows);
                $("#bodyModalSeeQueryStep2").html(html);
                console.log(html)
            })
    }
</script>
</body>
</html>
