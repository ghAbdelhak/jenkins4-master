// Payment confirmation handler
$('.btnConfirmPayment').on('click', function(e) {
    e.preventDefault();
    let amountValue = formatNumber($('#amount').val());
    let user = " de l'employé: "+ $('#idUser option:selected').text();
    if(user === " de l'employé: " ) {
        user = " du client: "+$('#idCustomer option:selected').text();
    }
    $('#transactionMessage').text(`Êtes-vous sûr de valider le versement de ${amountValue} DA de la part ${user }  ?`);
    // Show the modal
    $('#transactionModal').modal('show');
});

// Confirm button handler
$('#btnTransactionYes').on('click', function(e) {
    e.preventDefault();
    $('#paymentForm').submit();
    $('#withdrawalForm').submit();
    $('#transactionModal').modal('hide');
});

// Withdrawal confirmation handler
$('.btnConfirmWithdrawal').on('click', function(e) {
    e.preventDefault();
    let amountValue = formatNumber($('#amount').val());
    $('#transactionMessage').text(`Êtes-vous sûr de retirer le montant de ${amountValue} DA de la caisse`);
    // Show the modal
    $('#transactionModal').modal('show');
});



function formatNumber(number) {
    return parseFloat(number).toFixed(2)
        .replace('.', ',')
        .replace(/\B(?=(\d{3})+(?!\d))/g, '.');
}
