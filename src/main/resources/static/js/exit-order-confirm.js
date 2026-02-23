$('.btnConfirmDelivery').on('click', function (e) {
    e.preventDefault();
    let selectedUser = $('#idUser option:selected').text();
    if(selectedUser === "" ) {
         selectedUser = $('#idUser').val()
    }

    // Update the confirmation message
    $("#confirmMessage").text("Êtes-vous sûr de vouloir transférer cette commande à : " + selectedUser + " ?");

    // Copy the table content to the modal (only rows where quantity > 0)
    const originalTable = document.getElementById('products');
    const modalTableBody = document.getElementById('modalTableBody');
    const modalGrandTotal = document.getElementById('modalGrandTotal');

    // Clear previous content
    modalTableBody.innerHTML = '';

    // Get all rows from the original table (skip header and footer)
    const rows = originalTable.querySelectorAll('tbody tr');

    // Copy each row to the modal table ONLY if quantity > 0
    rows.forEach(row => {
        const quantityInput = row.querySelector('input[type="number"]');
        const quantity = parseFloat(quantityInput.value) || 0;

        if (quantity > 0) {  // Only include rows with quantity > 0
            const cells = row.querySelectorAll('td');
            if (cells.length >= 9) { // Make sure we have enough cells
                const newRow = document.createElement('tr');
                newRow.innerHTML = `
                    <td>${cells[2].textContent}</td>
                    <td>${cells[3].textContent}</td>
                    <td>${cells[4].textContent}</td>
                    <td>${cells[5].textContent}</td>
                    <td>${cells[6].textContent}</td>
                    <td>${quantityInput.value}</td>
                    <td>${cells[9].textContent}</td>
                `;
                modalTableBody.appendChild(newRow);
            }
        }
    });

    // Copy the grand total
    modalGrandTotal.textContent = document.getElementById('grandTotal').value;

    // Show the modal
    $('#confirmModal').modal('show');
});

$('#confirmModal #btnConfirmYes').on('click', function(e) {
    e.preventDefault();
    $('#deliveryForm').submit();
    $('#confirmModal').modal('hide');
});