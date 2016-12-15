define(function() {
  var
    irdeto = window.irdeto || (window.irdeto = {}),
    TranslationResolver = irdeto.ns.use('translate.TranslationResolver'),
    t = TranslationResolver.method('get');
 
 
  TranslationResolver.fetch([
    'First Name',
    'Last Name',
    'Contribution'
  ]);
 
  return {
    type: 'person',
    columns: [
      {
        label: t('First Name'),
        field: 'firstName',
        renderer: function(context) {
          context.value = irdeto.control.DataGrid.cellRenderers.inspectLink(context);
          return context.value;
        },
        sortable: true,
        filterable: true,
        width: 'auto'
      },
      {
        label: t('Last Name'),
        field: 'lastName',
        renderer: function(context) {
          context.value = irdeto.control.DataGrid.cellRenderers.inspectLink(context);
          return context.value;
        },
        sortable: true,
        filterable: true,
        width: 'auto'
      },
      {
        label: t('Contribution'),
        field: 'contribution',
        renderer: function(context) {
	  context.value = irdeto.control.DataGrid.cellRenderers.dataProviderAware(context);
          context.value = irdeto.control.DataGrid.cellRenderers.inspectLink(context);
          return context.value;
        },
        sortable: true,
        filterable: true,
        width: 'auto'
      }
    ],
    sort: [
      { field: 'lastName', direction: 'asc' },
      { field: 'firstName', direction: 'asc' }
    ]
  };
});
