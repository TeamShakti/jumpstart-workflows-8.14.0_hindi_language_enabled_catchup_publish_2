define(function() {
  var
    irdeto = window.irdeto || (window.irdeto = {}),
    TranslationResolver = irdeto.ns.use('translate.TranslationResolver'),
    t = TranslationResolver.method('get');
 
 
  TranslationResolver.fetch([
    'URI ID',
    'Event Broadcast Date',
    'Title'
  ]);
 
  return {
    type: 'event',
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
        label: t('Event Broadcast Date'),
        field: 'eventBroadcastDate',
        dateFormat: 'YYYY-MM-DD H:mm:ss',
        dateTimezone: 'LOCAL',
        renderer: function(context) {
          context.value = irdeto.control.DataGrid.cellRenderers.timestampAware.call(this, context);
          context.value = irdeto.control.DataGrid.cellRenderers.inspectLink(context);
          return context.value;
        },
        sortable: true,
        filterable: true,
        width: '150px'
      },
      {
        label: t('Title'),
        field: 'titleBrief',
        renderer: function(context) {
          context.value = irdeto.control.DataGrid.cellRenderers.localeAware(context);
          context.value = irdeto.control.DataGrid.cellRenderers.inspectLink(context);
          return context.value;
        },
        sortable: true,
        filterable: true,
        width: '300px'
      }
    ],
    sort: [
      { field: 'eventBroadcastDate', direction: 'desc' }
    ]
  };
});
