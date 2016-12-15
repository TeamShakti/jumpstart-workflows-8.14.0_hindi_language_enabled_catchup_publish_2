define(function() {
  var
    irdeto = window.irdeto || (window.irdeto = {}),
    TranslationResolver = irdeto.ns.use('translate.TranslationResolver'),
    t = TranslationResolver.method('get');
 
 
  TranslationResolver.fetch([
    'Name',
    'Device Class',
    'Enabled'
  ]);
 
  return {
    type: 'deviceprofile',
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
        label: t('Device Class'),
        field: 'deviceClass',
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
        label: t('Enabled'),
        field: 'enabled',
        renderer: function(context) {
          // wrap the title with a link to inspect the entity
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
      { field: 'name', direction: 'asc' }
    ]
  };
});
