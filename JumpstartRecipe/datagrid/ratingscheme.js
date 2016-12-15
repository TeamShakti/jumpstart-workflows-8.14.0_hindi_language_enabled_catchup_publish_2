define(function() {
  var
    irdeto = window.irdeto || (window.irdeto = {}),
    TranslationResolver = irdeto.ns.use('translate.TranslationResolver'),
    t = TranslationResolver.method('get');
 
 
  TranslationResolver.fetch([
    'Classification System'
  ]);
 
  return {
    type: 'ratingScheme',
    columns: [
      {
        label: t('Classification System'),
        field: 'classification',
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
      { field: 'classification', direction: 'asc' }
    ]
  };
});
