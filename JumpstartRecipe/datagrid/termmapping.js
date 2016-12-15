define(function() {
  var
    irdeto = window.irdeto || (window.irdeto = {}),
    TranslationResolver = irdeto.ns.use('translate.TranslationResolver'),
    t = TranslationResolver.method('get');
 
 
  TranslationResolver.fetch([
    'Contract Name',
    'Policy Group ID',
    'Policy Type',
    'Policy ID'
  ]);
 
  return {
    type: 'termmapping',
    columns: [
      {
        label: t('Contract Name'),
        field: 'contractName',
        renderer: function(context) {
          context.value = irdeto.control.DataGrid.cellRenderers.inspectLink(context);
          return context.value;
        },
        sortable: true,
        filterable: true,
        width: 'auto'
      },
      {
          label: t('Policy Group ID'),
          field: 'policyGroupId',
          renderer: function(context) {
            context.value = irdeto.control.DataGrid.cellRenderers.inspectLink(context);
            return context.value;
          },
          sortable: true,
          filterable: true,
          width: 'auto'
        },
        {
            label: t('Policy Type'),
            field: 'policyType',
            renderer: function(context) {
                context.value = irdeto.control.DataGrid.cellRenderers.dataProviderAware(context);
              context.value = irdeto.control.DataGrid.cellRenderers.inspectLink(context);
              return context.value;
            },
            sortable: true,
            filterable: true,
            width: 'auto'
          },
      {
        label: t('Policy ID'),
        field: 'policyId',
        renderer: function(context) {
          context.value = irdeto.control.DataGrid.cellRenderers.inspectLink(context);
          return context.value;
        },
        sortable: true,
        filterable: true,
        width: 'auto'
      }
    ],
    sort: [
      { field: 'contractName', direction: 'asc' }
    ]
  };
});
