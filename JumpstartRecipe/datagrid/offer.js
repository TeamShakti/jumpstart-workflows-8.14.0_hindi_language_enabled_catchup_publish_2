define(function() {
  var
    irdeto = window.irdeto || (window.irdeto = {}),
    TranslationResolver = irdeto.ns.use('translate.TranslationResolver'),
    t = TranslationResolver.method('get');
 
 
  TranslationResolver.fetch([
    'URI ID',
    'Billing ID',
    'Start Date/Time',
    'End Date/Time',
    'Approved'
  ]);
 
  return {
    type: 'offer',
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
        label: t('Billing ID'),
        field: 'billingId',
        renderer: function(context) {
          context.value = irdeto.control.DataGrid.cellRenderers.inspectLink(context);
          return context.value;
        },
        sortable: true,
        filterable: true,
        width: '100px'
      },
      {
        label: t('Start Date/Time'),
        field: 'startDateTime',
        dateFormat: 'YYYY-MM-DD',
        dateTimezone: 'LOCAL',
        renderer: function(context) {
          context.value = irdeto.control.DataGrid.cellRenderers.timestampAware.call(this, context);
          context.value = irdeto.control.DataGrid.cellRenderers.inspectLink(context);
          return context.value;
        },
        sortable: true,
        filterable: true,
        width: '120px'
      },
      {
        label: t('End Date/Time'),
        field: 'endDateTime',
        dateFormat: 'YYYY-MM-DD',
        dateTimezone: 'LOCAL',
        renderer: function(context) {
          context.value = irdeto.control.DataGrid.cellRenderers.timestampAware.call(this, context);
          context.value = irdeto.control.DataGrid.cellRenderers.inspectLink(context);
          return context.value;
        },
        sortable: true,
        filterable: true,
        width: '120px'
      },
      {
        label: t('Approved'),
        field: 'metadataApproved',
        renderer: function(context) {
          context.value = irdeto.control.DataGrid.cellRenderers.booleanAware(context);
          context.value = irdeto.control.DataGrid.cellRenderers.inspectLink(context);
          return context.value;
        },
        sortable: true,
        filterable: true,
        width: '200px'
      }
    ],
    sort: [
      { field: 'billingId', direction: 'asc' },
      { field: 'uriId', direction: 'asc' }
    ]
  };
});
