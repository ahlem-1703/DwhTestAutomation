<%--
  Created by IntelliJ IDEA.
  User: yassi
  Date: 31/05/2023
  Time: 17:10
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="modal fade" id="SeeResultQueryModalStep3" tabindex="-1"
     role="dialog" aria-labelledby="SeeResultQueryModalStep3"
     aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content" id="bodyModalSeeQueryStep3">

    </div>

  </div>
</div>
<script type="text/javascript">
  function SeeResultQueryModalFunctionStep3(){

    var html = '';

    var form = document.getElementById('formkpi'); // Replace 'yourFormId' with the actual ID of your form
    var formData = new FormData(form);
    fetch('/LaunchScriptTestResultStep3',{
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
              var modal = document.getElementById('exampleModalLong4');
              var modalInstance = new bootstrap.Modal(modal);
              modalInstance.hide();
              html = renderHtml(data.columns,data.rows);
              $("#bodyModalSeeQueryStep3").html(html);
              console.log(html)
            })
  }
</script>
</body>
</html>
