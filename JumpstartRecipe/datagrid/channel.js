define(function() {
  var
    irdeto = window.irdeto || (window.irdeto = {}),
    TranslationResolver = irdeto.ns.use('translate.TranslationResolver'),
    t = TranslationResolver.method('get');
 
 
  TranslationResolver.fetch([
    'URI ID',
    'Display Order',
    'Title',
    'Enabled'
  ]);
 
  return {
    type: 'channel',
    columns: [
      {
        label: t('URI ID'),
        field: 'uriId',
        renderer: function(context) {
          context.value = irdeto.control.DataGrid.cellRenderers.inspectLink(context);
          return context.value;
        },
        sortable: true,
        filterable: true,
        width: 'auto'
      },
      {
        label: t('Order'),
        field: 'displayOrder',
        renderer: function(context) {
          context.value = irdeto.control.DataGrid.cellRenderers.inspectLink(context);
          return context.value;
        },
        sortable: true,
        filterable: true,
        width: '60px'
      },
      {
        label: t('Title'),
        field: 'titleLong',
        renderer: function(context) {
          context.value = irdeto.control.DataGrid.cellRenderers.localeAware(context);
          context.value = irdeto.control.DataGrid.cellRenderers.inspectLink(context);
          return context.value;
        },
        sortable: true,
        filterable: true,
        width: '300px'
      },
      {
        label: t('Enabled'),
        field: 'enabled',
        renderer: function(context) {
          context.value = irdeto.control.DataGrid.cellRenderers.booleanAware(context);
          context.value = irdeto.control.DataGrid.cellRenderers.inspectLink(context);
          return context.value;
        },
        sortable: true,
        filterable: true,
        width: '100px'
      }
    ],
    sort: [
      { field: 'displayOrder', direction: 'asc' }
    ]
  };
});
