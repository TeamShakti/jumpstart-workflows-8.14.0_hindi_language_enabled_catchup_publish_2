define(function() {
  var
    irdeto = window.irdeto || (window.irdeto = {}),
    TranslationResolver = irdeto.ns.use('translate.TranslationResolver'),
    t = TranslationResolver.method('get');


  TranslationResolver.fetch([
    'Channel',
    'Broadcast Date',
    'Start Time',
    'End Time',
    'Title',
    'Genre',
    'Rating'
  ]);

  return {
    type: 'scheduleSlot',
    columns: [
      {
        label: t('Date'),
        field: 'linearBroadcastDate',
        dateFormat: 'YYYY-MM-DD',
        dateTimezone: 'LOCAL',
        renderer: function(context) {
          context.value = irdeto.control.DataGrid.cellRenderers.timestampAware.call(this, context);
          context.value = irdeto.control.DataGrid.cellRenderers.inspectLink(context);
          return context.value;
        },
        sortable: true,
        filterable: true,
        width: '100px'
      },
      {
        label: t('Start'),
        field: 'linearBroadcastDate',
        dateFormat: 'H:mm:ss',
        dateTimezone: 'LOCAL',
        renderer: function(context) {
          context.value = irdeto.control.DataGrid.cellRenderers.timestampAware.call(this, context);
          context.value = irdeto.control.DataGrid.cellRenderers.inspectLink(context);
          return context.value;
        },
        sortable: true,
        filterable: true,
        width: '90px'
      },
      {
        label: t('End'),
        field: 'linearBroadcastEndDate',
        dateFormat: 'H:mm:ss',
        dateTimezone: 'LOCAL',
        renderer: function(context) {
          context.value = irdeto.control.DataGrid.cellRenderers.timestampAware.call(this, context);
          context.value = irdeto.control.DataGrid.cellRenderers.inspectLink(context);
          return context.value;
        },
        sortable: true,
        filterable: true,
        width: '90px'
      },
      {
        label: t('Title'),
        field: 'title',
        renderer: function(context) {
          context.value = irdeto.control.DataGrid.cellRenderers.localeAware(context);
          context.value = irdeto.control.DataGrid.cellRenderers.inspectLink(context);
          return context.value;
        },
        sortable: true,
        filterable: true,
        width: 'auto'
      },
      {
        label: t('Genre'),
        field: 'genre',
        renderer: function(context) {
          context.value = irdeto.control.DataGrid.cellRenderers.localeAware(context);
          context.value = irdeto.control.DataGrid.cellRenderers.inspectLink(context);
          return context.value;
        },
        sortable: true,
        filterable: true,
        width: '150px'
      },
      {
        label: t('Rating'),
        field: 'rating',
        renderer: function(context) {
          context.value = irdeto.control.DataGrid.cellRenderers.inspectLink(context);
          return context.value;
        },
        sortable: true,
        filterable: true,
        width: '100px'
      },
      {
        label: t('Catch Up'),
        field: 'catchUp',
        renderer: function(context) {
          context.value = irdeto.control.DataGrid.cellRenderers.booleanAware(context);
          context.value = irdeto.control.DataGrid.cellRenderers.inspectLink(context);
          return context.value;
        },
        sortable: true,
        filterable: true,
        width: '100px'
      },
    ],
    sort: [
      { field: 'linearBroadcastDate', direction: 'asc' }
    ]
  };
});
