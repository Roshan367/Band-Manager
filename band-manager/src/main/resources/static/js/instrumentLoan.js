function confirmUpdate(){
    return confirm("Are you sure you want to return this instrument loan?");
}

$(function () {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
});

function returnInstrumentLoan(id){
    $.ajax({
        url: '/instrument-loan/' + id,
        type: 'PATCH',
        contentType: 'application/json',
        data: JSON.stringify({
            returned: true,
        }),

        success: function(response){
            alert(response);
            window.location.href = "/loans";
        },
        error: function(xhr){
            alert("Error returning instrument loan: " + xhr.responseText);
        }
    });
}

$(document).ready(function(){
    $('#returnInstrumentLoanButton').click(function(event){
        event.preventDefault();
        const id = $(this).data('id');
        if(confirmUpdate()){
            returnInstrumentLoan(id);
        }
    });
})
