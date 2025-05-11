require "roda"
require "json"

class App < Roda
  plugin :common_logger, $stdout

  DEVICES = {
    1 => { id: 1, owner_id: 1, model_id: 16 },
    2 => { id: 2, owner_id: 1, model_id: 2 },
    3 => { id: 3, owner_id: 2, model_id: 18 },
    4 => { id: 4, owner_id: 2, model_id: 1 },
    5 => { id: 5, owner_id: 3, model_id: 10 },
    6 => { id: 6, owner_id: 3, model_id: 14 },
    7 => { id: 7, owner_id: 3, model_id: 9 },
    8 => { id: 8, owner_id: 4, model_id: 25 },
    9 => { id: 9, owner_id: 4, model_id: 27 },
  }

  OWNERS = {
    1 => { id: 1, name: 'brave Paul' },
    2 => { id: 2, name: 'pretty Holly' },
    3 => { id: 3, name: 'little Kate' },
    4 => { id: 4, name: 'uncle Fred' },
  }

  DEVICE_MODELS = {
    1 => { id: 1, producer_id: 1, kind_id: 1, name: 'M1' },
    2 => { id: 2, producer_id: 1, kind_id: 1, name: 'A15' },
    3 => { id: 3, producer_id: 1, kind_id: 1, name: 'A16' },
    4 => { id: 4, producer_id: 1, kind_id: 1, name: 'A25' },
    5 => { id: 5, producer_id: 1, kind_id: 1, name: 'A35' },
    6 => { id: 6, producer_id: 1, kind_id: 1, name: 'S22' },
    7 => { id: 7, producer_id: 1, kind_id: 1, name: 'S23' },
    8 => { id: 8, producer_id: 1, kind_id: 1, name: 'S24' },
    9 => { id: 9, producer_id: 1, kind_id: 1, name: 'S25' },
    10 => { id: 10, producer_id: 2, kind_id: 1, name: 'G7' },
    11 => { id: 11, producer_id: 2, kind_id: 1, name: 'G6' },
    12 => { id: 12, producer_id: 2, kind_id: 1, name: 'G5' },
    13 => { id: 13, producer_id: 2, kind_id: 1, name: 'G4' },
    14 => { id: 14, producer_id: 2, kind_id: 1, name: 'G3' },
    15 => { id: 15, producer_id: 2, kind_id: 1, name: 'Q7' },
    16 => { id: 16, producer_id: 2, kind_id: 1, name: 'Q6' },
    17 => { id: 17, producer_id: 2, kind_id: 1, name: 'Q5' },
    18 => { id: 18, producer_id: 2, kind_id: 1, name: 'V40' },
    19 => { id: 19, producer_id: 2, kind_id: 1, name: 'V30' },
    20 => { id: 20, producer_id: 2, kind_id: 1, name: 'V20' },
    21 => { id: 21, producer_id: 2, kind_id: 1, name: 'V10' },
    22 => { id: 22, producer_id: 4, kind_id: 1, name: 'Xperia 1 V' },
    23 => { id: 23, producer_id: 4, kind_id: 1, name: 'Xperia 1 IV' },
    24 => { id: 24, producer_id: 4, kind_id: 1, name: 'Xperia 1 III' },
    25 => { id: 25, producer_id: 4, kind_id: 1, name: 'Xperia 1 II' },
    26 => { id: 26, producer_id: 4, kind_id: 1, name: 'Xperia 5 V' },
    27 => { id: 27, producer_id: 4, kind_id: 1, name: 'Xperia 5 IV' },
    28 => { id: 28, producer_id: 4, kind_id: 1, name: 'Xperia 5 III' },
    29 => { id: 29, producer_id: 4, kind_id: 1, name: 'Xperia 5 II' },
    71 => { id: 71, producer_id: 3, kind_id: 2, name: 'Pavilion 15' },
    72 => { id: 72, producer_id: 3, kind_id: 2, name: 'Pavilion 16' },
    73 => { id: 73, producer_id: 3, kind_id: 2, name: 'OmniBook Ultra 14' },
    74 => { id: 74, producer_id: 3, kind_id: 2, name: 'OmniBook X 14' },
  }

  DEVICE_KINDS = {
    1 => { id: 1, name: 'mobile phone' },
    2 => { id: 2, name: 'laptop' },
    3 => { id: 3, name: 'pc' },
  }

  PRODUCERS = {
    1 => { id: 1, title: 'Samsung' },
    2 => { id: 2, title: 'LG' },
    3 => { id: 3, title: 'Hp' },
    4 => { id: 4, title: 'Sony' }
  }

  plugin :json

  def prepare_group_filter(params)
    raw_filter_key, raw_filter_value = params.except('ids').to_a.shift
    filter_key = raw_filter_key&.to_sym
    filter_values = raw_filter_value if raw_filter_value.is_a?(Array)
    filter_values ||= [raw_filter_value] if raw_filter_value
    filter_values = filter_values&.map {|v| v =~ /^\d+$/ ? v.to_i : v }
    [filter_key, filter_values]
  end

  def search(collection, params, &block)
    items = collection.values_at(*params['ids']&.map(&:to_i)) if params.key?('ids')
    items ||= collection.values
    filter_key, filter_values = prepare_group_filter(params)
    return items unless filter_key || block_given?
    return items.map(&block) unless filter_key

    filter_values.map do |value|
      items.select { _1[filter_key] == value }.map(&(block || :itself))
    end
  end

  route do |r|
    r.on "api" do
      r.on "v1" do
        r.on "devices" do
          r.is do
            search(DEVICES, r.params)
          end

          r.is Integer do |equipment_id|
            DEVICES[equipment_id]
          end
        end

        r.on "owners" do
          r.is do
            search(OWNERS, r.params)
          end
        end

        r.on "device_models" do
          r.is do
            search(DEVICE_MODELS, r.params) { _1&.merge(kind: DEVICE_KINDS[_1[:kind_id]]) }
          end
        end

        r.on "device_kinds" do
          r.is do
            search(DEVICE_KINDS, r.params)
          end
        end

        r.on "producers" do
          r.is do
            search(PRODUCERS, r.params)
          end

          r.is Integer do |producer_id|
            PRODUCERS[producer_id]
          end
        end
      end
    end
  end
end

run App.freeze.app
