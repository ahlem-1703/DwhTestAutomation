<div id="dailySchedule" style="display: block;">
  <div>
    <label> Execute Every day at : </label> <input class="form-group custom-time-input" type="time" id="dailyExecutionTime" name="dailyExecutionTime"/><style>
    /* Styles pour l'input de type time personnalisé */
    .custom-time-input {
      padding: 8px;
      border: 1px solid #ccc;
      border-radius: 5px;
      font-size: 16px;
      background-color: #fff;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
      width: 30%;
    }

    /* Style pour le focus de l'input */
    .custom-time-input:focus {
      border-color: #007BFF;
      outline: none;
      box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
    }
  </style>


  </div>
  <br>
  <div>
    <label>Execute this schedule on the following day(s):</label>
    <br>
    <br>
    <input class="flat" type="checkbox" id="monday" name="dailyDaysOfWeek" value="MONDAY" checked>
    <label for="monday">Monday</label>
    <input class="flat" type="checkbox" id="tuesday" name="dailyDaysOfWeek" value="TUESDAY" checked>
    <label for="tuesday">Tuesday</label>
    <input class="flat" type="checkbox" id="wednesday" name="dailyDaysOfWeek" value="WEDNESDAY" checked>
    <label for="wednesday">Wednesday</label>
    <input class="flat" type="checkbox" id="thursday" name="dailyDaysOfWeek" value="THURSDAY" checked>
    <label for="thursday">Thursday</label>
    <input class="flat" type="checkbox" id="friday" name="dailyDaysOfWeek" value="FRIDAY" checked>
    <label for="friday">Friday</label>
    <input class="flat" type="checkbox" id="saturday" name="dailyDaysOfWeek" value="SATURDAY" checked>
    <label for="saturday">Saturday</label>
    <input class="flat" type="checkbox" id="sunday" name="dailyDaysOfWeek" value="SUNDAY" checked>
    <label for="sunday">Sunday</label>
  </div>
  <br>
  <div>
    <label for="endAtDaily">End at :</label>
    <input class="form-group custom-time-input" type="date" name="endAtDaily" id="endAtDaily">
  </div>
  <br>
</div>
