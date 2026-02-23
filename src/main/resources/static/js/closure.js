$('.btnClosureCash').on('click', function (e) {
    e.preventDefault()
    $("#closureMessage").text("Etes-vous sûr de cloturer la caisse aujourd'hui ?")
    $('#closureModal').modal('show')
});

$('#btnClosureYes').on('click', function (e) {
    e.preventDefault()
    $("#cashClosureForm").submit()
    $('#closureModal').hide()
});
