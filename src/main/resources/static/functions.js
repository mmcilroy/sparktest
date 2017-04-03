$(document).ready(function() {

  $('#session-dd').on('show.bs.dropdown', function() {
    $.getJSON('sessions', function(data) {
      var sessionList = $('#session-list');
      sessionList.html('');
      $.each(data, function(i, obj) {
        sessionList.append('<li><a href="#">'+obj+'</a></li>');
      });
    });
  });

  $(document).on('click', '#session-list li a', function() {
    var session = $(this).text();
    $.getJSON('messages?session='+session, function(data) {
      var messageTable = $('#message-table');
      messageTable.html('');
      $.each(data, function(i, obj) {
        messageTable.append('<tr><td>'+obj+'</td></tr>');
      });
    });
  });

  $(document).on('click', '#message-table td', function() {
    var fieldsTable = $('#fields-table');
    fieldsTable.html('');
    var message = $(this).text();
    var fields = message.split('|');
    fields.forEach(function(field) {
        fieldsTable.append('<tr><td>'+field+'</td></tr>');
    });
  });
});
