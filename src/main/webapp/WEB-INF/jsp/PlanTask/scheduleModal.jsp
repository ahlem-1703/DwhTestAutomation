

<div class="modal fade" id="scheduleModal" tabindex="-1"
     role="dialog" aria-labelledby="exampleModalLong2"
     aria-hidden="true">
    <div style="max-width: 600px;width: 100%;" class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Schedule</h5>
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div style="margin: 20px">
                <style>
                    /* Styles CSS internes */
                    .modal-content {
                        background-color: #f8f8f8;
                        border-radius: 10px;
                    }

                    .modal-title {
                        font-size: 24px;
                    }

                    .form-control {
                        border: 1px solid #ccc;
                        border-radius: 5px;
                        margin-bottom: 10px;
                    }

                    .btn-primary {
                        background-color: #007BFF;
                        color: #fff;
                        border: none;
                        border-radius: 5px;
                        padding: 10px 20px;
                    }

                    .btn-primary:hover {
                        background-color: #0056b3;
                    }

                    label {
                        font-weight: bold;
                    }

                    select.custom-select {
                        width: 100%;
                    }

                    .close {
                        font-size: 24px;
                    }
                    /* Styles pour la case à cocher personnalisée */

                </style>


<div class="item form-group">
                    <label for="scheduleName">Schedule Name:</label>
                    <input class="form-control" type="text" id="scheduleName" name="scheduleName"placeholder="The name must be unique" />
                </div>
                <br>

                <div class="item form-group">
                    <label for="triggerType">Trigger Type:</label>
                    <div class="col-md-3 col-sm-3 ">
                        <select class="browser-default custom-select" id="triggerType" name="triggerType"
                                onchange="handleTriggerTypeChange()">
                            <option value="DAILY">Daily</option>
                            <option value="WEEKLY">Weekly</option>
                            <option value="MONTHLY">Monthly</option>
                        </select>
                    </div>
                </div>
                <br>
                <%@include file="./dailySchedule.jsp" %>
                <%@include file="./monthlySchedule.jsp" %>
                <%@include file="./weeklySchedule.jsp" %>


                <center>
                    <button type="button" class="btn btn-primary"
                            data-dismiss="modal"
                            onClick="scheduleAPI()">
                        <!-- here was an syntax error. you were calling method by uts name without () sign -->
                        Plan task
                    </button>
                </center>
            </div>
        </div>
    </div>
</div>

