$("#tableFilter").on("keyup", function() {
        let value = $(this).val().toLowerCase();
        $("#products tr").filter(function() {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });


$('.btnDeleteSupplier').on('click', function (e) {
    e.preventDefault()
    let tdSibling = $(this).parent().siblings();
    let name = tdSibling.eq(0).text();
    $("#deleteMessage").text("Etes-vous sûr de supprimer le fournisseur "+name +"?")
    let href =$(this).attr('href');
    $('#deleteModal #btnDeleteYes').attr('href',href);
    $('#deleteModal').modal()
});

$('.btnArchiveOrder').on('click', function (e) {
    e.preventDefault()
    let tdSibling = $(this).parent().parent().siblings();
    let orderId = tdSibling.eq(0).text();
    $("#archiveMessage").text("Etes-vous sûr d'archiver la commande "+orderId +" ?")
    let href =$(this).attr('href');
    $('#archiveModal #btnArchiveYes').attr('href',href);
    $('#archiveModal').modal()
});

$('.btnArchiveAllOrders').on('click', function (e) {
    e.preventDefault()
    $("#archiveMessage").text("Etes-vous sûr d'archiver toutes les commandes livrées ?")
    let href =$(this).attr('href');
    $('#archiveModal #btnArchiveYes').attr('href',href);
    $('#archiveModal').modal()
});

$('.btnCancelOrder').on('click', function (e) {
    e.preventDefault()
    let tdSibling = $(this).parent().parent().siblings();
    let orderId = tdSibling.eq(0).text();
    $("#cancelMessage").text("Etes-vous sûr d'annuler la commande "+orderId +" ?")
    let href =$(this).attr('href');
    $('#cancelModal #btnCancelYes').attr('href',href);
    $('#cancelModal').modal()
});





