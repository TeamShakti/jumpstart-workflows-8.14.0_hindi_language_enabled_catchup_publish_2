define(function() {
  var
    irdeto = window.irdeto || (window.irdeto = {}),
    TranslationResolver = irdeto.ns.use('translate.TranslationResolver'),
    t = TranslationResolver.method('get');
 
 
  TranslationResolver.fetch([
    'Name',
    'Live URI'
    'Protection Type'
  ]);
 
  return {
    type: 'encodeProfile',
    columns: [
      {
        label: t('Name'),
        field: 'name',
        renderer: function(context) {
          context.value = irdeto.control.DataGrid.cellRenderers.inspectLink(context);
          return context.value;
        },
        sortable: true,
        filterable: true,
        width: 'auto'
      },
      {
        label: t('Live URI'),
        field: 'liveUri',
        renderer: function(context) {
          context.value = irdeto.control.DataGrid.cellRenderers.inspectLink(context);
          return context.value;
        },
        sortable: false,
        filterable: false,
        width: '40%'
      },
      {
        label: t('Protection Type'),
        field: 'protectionType',
        renderer: function(context) {
          context.value = irdeto.control.DataGrid.cellRenderers.dataProviderAware(context);
          context.value = irdeto.control.DataGrid.cellRenderers.inspectLink(context);
          return context.value;
        },
        sortable: false,
        filterable: false,
        width: '15%'
      }
    ],
    sort: [
      { field: 'name', direction: 'asc' }
    ]
  };
});
