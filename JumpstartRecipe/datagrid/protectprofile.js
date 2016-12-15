define(function() {
  var
    irdeto = window.irdeto || (window.irdeto = {}),
    TranslationResolver = irdeto.ns.use('translate.TranslationResolver'),
    t = TranslationResolver.method('get');
 
 
  TranslationResolver.fetch([
    'Name',
    'Protection Type',
    'Protection Workflow'
  ]);
 
  return {
    type: 'protectProfile',
    columns: [
      {
        label: t('Name'),
        field: 'name',
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
        label: t('Protection Type'),
        field: 'protectionType',
        renderer: function(context) {
          // wrap the title with a link to inspect the entity
          context.value = irdeto.control.DataGrid.cellRenderers.dataProviderAware(context);
          context.value = irdeto.control.DataGrid.cellRenderers.inspectLink(context);
          return context.value;
        },
        sortable: true,
        filterable: true,
        width: '200px'
      }
    ],
    sort: [
      { field: 'name', direction: 'asc' }
    ]
  };
});
