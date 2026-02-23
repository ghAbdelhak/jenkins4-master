$('#tableFilter').keyup(function() {
        let value = $(this).val().toLowerCase();
        $('#products tbody tr').filter(function() {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });

    // Pack confirmation handler
    $('.btnConfirmPack').on('click', function(e) {
        e.preventDefault();

        const packName = $('#name').val();
        const packQuantity = $('#quantity').val();
        const packPrice = $('#price').val();
        const packState = $('#state option:selected').text();

        // Update modal content
        $('#modalPackName').text(packName);
        $('#modalPackQuantity').text(packQuantity);
        $('#modalPackPrice').text(packPrice + ' DA');
        $('#modalPackState').text(packState);
        $('#packConfirmMessage').text(`Êtes-vous sûr de vouloir créer le pack "${packName}" ?`);

        // Copy the table content to the modal (only rows where quantity > 0)
        const originalTable = document.getElementById('products');
        const modalTableBody = document.getElementById('modalPackTableBody');

        // Clear previous content
        modalTableBody.innerHTML = '';

        // Get all rows from the original table
        const rows = originalTable.querySelectorAll('tbody tr');
        let hasProducts = false;

        // Copy each row to the modal table ONLY if quantity > 0
        rows.forEach(row => {
            const quantityInput = row.querySelector('.quantity-input');
            const quantity = parseFloat(quantityInput.value) || 0;

            if (quantity > 0) {
                hasProducts = true;
                const cells = row.querySelectorAll('td');
                if (cells.length >= 7) {
                    const newRow = document.createElement('tr');
                    newRow.innerHTML = `
                        <td>${cells[1].textContent}</td>
                        <td>${cells[2].textContent}</td>
                        <td>${cells[3].textContent}</td>
                        <td>${cells[4].textContent}</td>
                        <td>${cells[5].textContent}</td>
                        <td>${quantityInput.value}</td>
                    `;
                    modalTableBody.appendChild(newRow);
                }
            }
        });

        if (!hasProducts) {
            $('#packConfirmMessage').html(`
                <div class="alert alert-danger">
                    Attention: Vous n'avez sélectionné aucun produit pour ce pack!
                </div>
            `);
        }

        // Show the modal
        $('#confirmModal').modal('show');
    });

    // Confirm button handler
    $('#btnConfirmPackYes').on('click', function(e) {
        e.preventDefault();
        $('#packForm').submit();
        $('#confirmPackModal').modal('hide');
    });