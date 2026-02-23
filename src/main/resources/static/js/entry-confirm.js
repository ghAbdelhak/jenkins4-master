$(document).ready(function() {
    // Table filter functionality
    $('#tableFilter').keyup(function() {
        let value = $(this).val().toLowerCase();
        $('#products tbody tr').filter(function() {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });

    // Entry confirmation handler
    $('.btnConfirmEntry').on('click', function(e) {
        e.preventDefault();

        const entryNumber = $('#entryNumber').val();
        const entryDate = $('#registrationDate').val();
        const supplier = $('#idSupplier option:selected').text();

        // Update modal content
        $('#modalEntryNumber').text(entryNumber);
        $('#modalEntryDate').text(entryDate);
        $('#modalSupplier').text(supplier);
        $('#entryConfirmMessage').text(`Êtes-vous sûr de vouloir enregistrer cette entrée stock (${entryNumber}) ?`);

        // Copy the table content to the modal (only rows where quantity > 0)
        const originalTable = document.getElementById('products');
        const modalTableBody = document.getElementById('modalEntryTableBody');

        // Clear previous content
        modalTableBody.innerHTML = '';

        // Get all rows from the original table
        const rows = originalTable.querySelectorAll('tbody tr');
        let hasProducts = false;
        let totalQuantity = 0;

        // Copy each row to the modal table ONLY if quantity > 0
        rows.forEach(row => {
            const quantityInput = row.querySelector('input[type="number"][min="0"][step="1"]');
            const priceInput = row.querySelector('input[type="number"][min="0"][step="0.1"]');
            const quantity = parseFloat(quantityInput.value) || 0;
            const price = parseFloat(priceInput.value) || 0;

            if (quantity > 0) {
                hasProducts = true;
                totalQuantity += quantity;
                const cells = row.querySelectorAll('td');
                if (cells.length >= 6) {
                    const newRow = document.createElement('tr');
                    newRow.innerHTML = `
                        <td>${cells[1].textContent}</td>
                        <td>${cells[2].textContent}</td>
                        <td>${cells[3].textContent}</td>
                        <td>${price.toFixed(2)} DA</td>
                        <td>${quantity}</td>
                    `;
                    modalTableBody.appendChild(newRow);
                }
            }
        });

        if (!hasProducts) {
            $('#entryConfirmMessage').html(`
                <div class="alert alert-danger">
                    Attention: Vous n'avez sélectionné aucun produit pour cette entrée stock!
                </div>
            `);
        } else {
            // Add summary row
            const summaryRow = document.createElement('tr');
            summaryRow.className = 'table-info fw-bold';
            summaryRow.innerHTML = `
                <td colspan="3" class="text-end">Total:</td>
                <td></td>
                <td>${totalQuantity}</td>
            `;
            modalTableBody.appendChild(summaryRow);
        }

        // Show the modal only if there are products
        if (hasProducts) {
            $('#confirmEntryModal').modal('show');
        }
    });

    // Confirm button handler
    $('#btnConfirmEntryYes').on('click', function(e) {
        e.preventDefault();
        $('#entryForm').submit();
        $('#confirmEntryModal').modal('hide');
    });
});