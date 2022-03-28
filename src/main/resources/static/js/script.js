$(document).ready(function () {
    $('.sortable').dataTable({
        "pageLength": 25,
        language: {
            "processing": "Processant...",
            "lengthMenu": "Mostrar _MENU_ registres",
            "zeroRecords": "No s'ha trobat cap resultat",
            "emptyTable": "Cap dada disponible en aquesta taula",
            "infoEmpty": "Mostrant registres del 0 al 0 d'un total de 0 registres",
            "infoFiltered": "(filtrat d'un total de _MAX_ registres)",
            "search": "Cercar:",
            "infoThousands": ",",
            "loadingRecords": "Carregant...",
            "paginate": {
                "first": "Primer",
                "last": "Últim",
                "next": "Següent",
                "previous": "Anterior"
            },
            "aria": {
                "sortAscending": ": Activar per ordenar la columna de manera ascendent",
                "sortDescending": ": Activar per ordenar la columna de manera descendent"
            }
        }
    });
});