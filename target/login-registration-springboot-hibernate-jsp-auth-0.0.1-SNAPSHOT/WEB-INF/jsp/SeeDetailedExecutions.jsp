<html>
<head>
  <title>Detailed executions result</title>
</head>
<body>
<div class="modal fade custom-modal" id="SeeDetailedExecutions" tabindex="-1"
     role="dialog" aria-labelledby="SeeDetailedExecutions"
     aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content" id="bodySeeDetailedExecutions">

    </div>

  </div>
</div>
<script type="text/javascript">
  var executionListTablefiltersConfig = {
    base_path: 'tablefilter/',
    col_0: 'Status',
    col_1: 'Execution Time',
    popup_filters: true,
    auto_filter: {
      delay: 1100 //milliseconds
    },
    alternate_rows: true,
    rows_counter: true,
    btn_reset: true,
    status_bar: true,
    msg_filter: 'Filtering...',
    col_widths: [
      '125px', '90px', '100px',
      '90px', '90px', '90px',
      '90px', '90px', '90px'
    ]
  };
  var executionListTablef = new TableFilter('executionListTable', executionListTablefiltersConfig);
  function SeeDetailedExecutionsFunction(id) {
    var html = '';
    fetch('/getDetailedExecutions/' + id, {
      method: 'GET'
    })
            .then(function (response) {
              if (response.ok) {
                return response.json();
              } else {
                throw new Error('Error: ' + response.statusText);
              }
            })
            .then(function (data) {
              console.log(data)
              html = renderHtml(data);
              $("#bodySeeDetailedExecutions").html(html);
              // Initialize DataTable when the modal is fully shown
              $('#SeeDetailedExecutions').on('shown.bs.modal', function () {
                $('#executionListTable').DataTable({
                  searching: false,
                  lengthMenu: [5,10, 20, 50, 100, 200],
                });
                executionListTablef.init();
              });
            })
  }
  function renderHtml(data) {
    var html = `<div class="modal-header">
                                                                <h5 class="modal-title">Execution Details</h5>
                                                                <button type="button" class="close" data-dismiss="modal"
                                                                        aria-label="Close">
                                                                    <span aria-hidden="true">&times;</span>
                                                                </button>
                                                            </div>
<table id="executionListTable" class="table table-striped table-bordered">
    <thead>
        <th>Execution Time</th>
        <th>Status</th>
    </thead>
    <tbody>`;
    data.forEach(function (row) {
      var color = row.status == "Succeeded" ? "green":"red";
      html += `<tr>`;
      html += `<td>` + row.latestRun + `</td>`;
      html += `<td style="color:`+color+`">` + row.status + `</td>`;
      html += `</tr>`;
    });
    html += `</tbody>
</table>`;
    return html;
  }
</script>
</body>
</html>