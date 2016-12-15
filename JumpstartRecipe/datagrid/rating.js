define(function() {
  var
    irdeto = window.irdeto || (window.irdeto = {}),
    TranslationResolver = irdeto.ns.use('translate.TranslationResolver'),
    t = TranslationResolver.method('get');
 
 
  TranslationResolver.fetch([
    'Rating',
    'Minimum Age'
  ]);
 
  return {
    type: 'rating',
    columns: [
      {
        label: t('Rating'),
        field: 'ratingLabel',
        renderer: function(context) {
          // wrap the title with a link to inspect the entity
          context.value = irdeto.control.DataGrid.cellRenderers.inspectLink(context);
          return context.value;
        },
        sortable: true,
        filterable: true,
        width: 'auto'
      },
      {
        label: t('Minimum Age'),
        field: 'minimumAge',
        renderer: function(context) {
          // wrap the title with a link to inspect the entity
          context.value = irdeto.control.DataGrid.cellRenderers.inspectLink(context);
          return context.value;
        },
        sortable: true,
        filterable: true,
        width: 'auto'
      }
    ],
    sort: [
      { field: 'minimumAge', direction: 'asc' }
    ]
  };
});
