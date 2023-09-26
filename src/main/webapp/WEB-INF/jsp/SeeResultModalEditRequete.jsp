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
    <link href="<c:url value="/resources/css/util.css"/>" rel="stylesheet">
</head>
<body>
<div class="modal fade" id="SeeResultQueryModal" tabindex="-1"
     role="dialog" aria-labelledby="SeeResultQueryModal"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content" id="bodyModalSeeQuery">

        </div>
        <div id="customToastContainer"></div>

    </div>
</div>
<script type="text/javascript">
    function SeeResultQueryModalFunction(){

        var html = '';

        var form = document.getElementById('formkpi'); // Replace 'yourFormId' with the actual ID of your form
        var formData = new FormData(form);
        var modal = document.getElementById('exampleModalLong2');
        var modalInstance = new bootstrap.Modal(modal);
        fetch('/LaunchScriptEditRequeteTestResult',{
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
                hideExampleModalLong('exampleModalLong2')
                if (data.error) {
                    hideExampleModalLong('SeeResultQueryModal')
                    showDelayedToast(data.error);
                } else {
                // Process the response data
                html = renderHtml(data.columns,data.rows);
                $("#bodyModalSeeQuery").html(html);
                console.log(html)}
            })
            .catch(function(error) {
                showDelayedToast(error.message);
            });
    }
    function hideExampleModalLong(name){
        var modal = document.getElementById(name);
        var modalInstance = new bootstrap.Modal(modal);
        modalInstance.hide();
    }
    function showDelayedToast(message) {
        setTimeout(function() {
            createToast(message);
        }, 500); // Adjust the delay as needed
    }
    function createToast(message){
        // Create the toast element
        var toast = document.createElement("div");
        toast.classList.add("custom-toast-error");
        toast.innerText = message;

        // Add the toast to the container
        var toastContainer = document.getElementById("customToastContainer");
        toastContainer.appendChild(toast);

        // Show the toast
        setTimeout(function() {
            toast.classList.add("show");
        }, 100);

        // Hide and remove the toast after a certain duration (e.g., 3 seconds)
        setTimeout(function() {
            toast.classList.remove("show");
            setTimeout(function() {
                toastContainer.removeChild(toast);
            }, 300);
        }, 3000);
    }
</script>
</body>
</html>
